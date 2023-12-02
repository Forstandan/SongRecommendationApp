package com.example.a4800app

import android.os.Bundle
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
//        file.writeText("")
        fileSetter(file)
        setContentView(R.layout.activity_main)
    }
}