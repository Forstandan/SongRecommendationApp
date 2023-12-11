package com.example.a4800app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.FileReader
import java.io.IOException

class SongListViewModel : ViewModel() {
    var songs = mutableListOf<Song>()

    var limit = 10
    val baseURL = "https://personal-music-recommendation.azurewebsites.net/api/search"
    val searchFunctionKey = "dkS5_6Zm8E-ElF4KzKlwPwZTDm-0_5d2_Q-Re5afhl-yAzFu-Ak5rg=="
    val client = OkHttpClient()

    init {
        viewModelScope.launch{
            getPlaylist()
        }
        setViewModelScope(viewModelScope)
    }

    companion object : Subject{
        private val observers = mutableListOf<Observer>()
        private lateinit var viewModelScope : CoroutineScope

        fun getViewModelScope() : CoroutineScope {
            return viewModelScope
        }

        fun setViewModelScope(viewModelScope: CoroutineScope) {
            this.viewModelScope = viewModelScope
        }

        override fun attachObserver(observer: Observer) {
            observers += observer
        }

        override fun detachObserver(observer: Observer) {
            observers -= observer
        }

        override fun notifyObserver() {
            for (observer in observers) {
                if (observer is SongListAdapter) {
                    observer.setViewModelScope(getViewModelScope())
                }
                observer.update()
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch { searchSongs(query, limit)}
    }

    fun setTrackLimit(limit: Int) {

    }

    fun getPlaylist() {
        songs.clear()
        if (MainActivity.file.length().toInt() != 0) {
            MainActivity.file.forEachLine { line ->
                songs += parseString(line)
            }
        }

        notifyObserver()
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
        songs.clear()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            Log.d("tag", "" + jsonObject)

            val song = Song(
                songURL = jsonObject.getString("external_url"),
                imageURL = jsonObject.getJSONArray("images").getJSONObject(0).getString("url"),
                name = jsonObject.getString("name"),
                artist = removeChar(jsonObject.getString("artists")),
            )

            songs += song
            Log.d("tag", "" + song)
        }

        notifyObserver()
    }

    private fun removeChar(string : String) : String {
        val result = string.replace(Regex("[\'\"\\[\\]]"), "")
        return result.replace(Regex(","), ", ")
    }

    private fun parseString(string : String) : Song {
        val tokens = string.split(",")
        Log.d("print string", tokens.toString())
        return Song(
            songURL = tokens[0],
            imageURL = tokens[1],
            name = tokens[2],
            artist = tokens[3]
        )
    }
}