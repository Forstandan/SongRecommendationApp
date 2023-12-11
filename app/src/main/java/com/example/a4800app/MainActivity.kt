package com.example.a4800app

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.io.File

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var file : File

        fun fileSetter(file : File) {
            this.file = file
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Picasso.get().setIndicatorsEnabled(true)
        val file = File(filesDir, "playlist.json")
        file.createNewFile()
        file.writeText("")
        fileSetter(file)
        setContentView(R.layout.activity_main)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_button -> {
                val songListViewModel: SongListViewModel by viewModels()
                songListViewModel.getPlaylist()
                SongListFragment.setIsViewingPlaylist(true)
            }
        }

        return super.onOptionsItemSelected(item)
    }


}