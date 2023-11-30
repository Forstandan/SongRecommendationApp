package com.example.a4800app

import androidx.lifecycle.ViewModel

class SongListViewModel : ViewModel() {
    val playlist = mutableListOf<Song>()

    init {
        val song = Song(
            imageURL = "https://i.scdn.co/image/ab67616d0000b2735fb6d9b4948a069f31bc003b",
            name = "Russian Overture, Op. 72",
            artist = "Sergei Prokofiev"
        )
        playlist += song
    }
}