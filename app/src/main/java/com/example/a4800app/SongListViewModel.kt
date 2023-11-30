package com.example.a4800app

import androidx.lifecycle.ViewModel

class SongListViewModel : ViewModel() {
    val playlist = mutableListOf<Song>()

    init {
        val song1 = Song(
            songURL = "https://open.spotify.com/track/6KWlIAypAbAjCH31fxBhab",
            imageURL = "https://i.scdn.co/image/ab67616d0000b2735fb6d9b4948a069f31bc003b",
            name = "Russian Overture, Op. 72",
            artist = "Sergei Prokofiev"
        )
        val song2 = Song(
            songURL = "https://open.spotify.com/track/1ipJdiNMjgvfAilddGT9sI",
            imageURL = "https://i.scdn.co/image/ab67616d0000b273e044ae00085e8ac1c98c43b5",
            name = "Fantasien op. 116: Intermezzo: Adagio",
            artist = "Johannes Brahms, Martin Tchiba"
        )
        val song3 = Song(
            songURL = "https://open.spotify.com/track/37TBjwOpcQIo9IhScN3XhL",
            imageURL = "https://i.scdn.co/image/ab67616d0000b273d70d80953428790b57f0918b",
            name = "Ensemble for String Quintet and Thunder Sticks (1956 version): I. Larghetto",
            artist = "Henry Cowell, Northwest Chamber Orchestra Seattle, Alun Francis"
        )
        val song4 = Song(
            songURL = "https://open.spotify.com/track/4P1sbofnneFeEEASTFMEGl",
            imageURL = "https://i.scdn.co/image/ab67616d0000b2731061524b7fe4e944c74173eb",
            name = "Main Title",
            artist = "James Horner"
        )
        val song5 = Song(
            songURL = "https://open.spotify.com/track/3hO3haFBsrcCzIFd6HJXnj",
            imageURL = "https://i.scdn.co/image/ab67616d0000b27366437708f58fc26c93c6d6e8",
            name = "24 Preludes, Op. 28: Prelude No. 4 in E Minor",
            artist = "Fr\u00e9d\u00e9ric Chopin, Evgeny Kissin"
        )
        playlist += song1
        playlist += song2
        playlist += song3
        playlist += song4
        playlist += song5

    }
}