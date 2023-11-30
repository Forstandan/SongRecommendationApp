package com.example.a4800app

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Picasso

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Picasso.get().setIndicatorsEnabled(true)
        setContentView(R.layout.activity_main)
    }
}