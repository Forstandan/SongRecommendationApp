package com.example.a4800app

import com.google.gson.Gson
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.json.JSONArray
import java.io.IOException

class SongListViewModel : ViewModel() {
    val playlist = mutableListOf<Song>()
    var searchResults = mutableListOf<Song>()

    var limit = 10
    val baseURL = "https://personal-music-recommendation.azurewebsites.net/api/search"
    val searchFunctionKey = "dkS5_6Zm8E-ElF4KzKlwPwZTDm-0_5d2_Q-Re5afhl-yAzFu-Ak5rg=="
    val client = OkHttpClient()

    init {
        viewModelScope.launch{

        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch { searchSongs(query, limit)}
    }

    fun setTrackLimit(limit: Int) {

    }

    private suspend fun searchSongs(query: String, limit: Int) {
        return withContext(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                try {
                    val url = "$baseURL?code=$searchFunctionKey&q=$query&type=track&limit=$limit"

                    val request = Request.Builder().url(url).build()

                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val jsonResponse = response.body()?.string()
                            Log.d("tag", "" + jsonResponse)
                            // Parse jsonString
                            parseJson(jsonResponse)
                        } else {
                            Log.e("tag", "HTTP request failed: ${response.code()}")
                        }
                    }
                } catch (e: IOException) {
                    Log.e("tag", "Error during network request", e)
                }
            }
        }
    }

    private fun parseJson(responseBody : String?) {
        val jsonArray = JSONArray(responseBody)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val song = Song(
                songURL = jsonObject.getString("external_url"),
                imageURL = jsonObject.getString("url"),
                name = jsonObject.getString("name"),
                artist = jsonObject.getString("artists"),
            )

            searchResults += song
        }
    }
}