package com.example.a4800app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a4800app.databinding.ListItemSongBinding
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileWriter

class SongHolder(val binding: ListItemSongBinding, songs: MutableList<Song>) : RecyclerView.ViewHolder(binding.root) {
    fun bind(song : Song) {
//        binding.root.setOnClickListener {
//            SongListAdapter.notifyObserver()
//        }
        binding.addToPlaylist.setOnClickListener {
            val string = "${binding.songUrl.text},${binding.imageUrl.text},${binding.songName.text},${binding.songArtist.text}\n"
            MainActivity.file.appendText(string)
            Log.d("file read", "" + MainActivity.file.readText())

            Toast.makeText(
                binding.root.context,
                "${song.name} added to playlist!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class SongListAdapter(private val songs: MutableList<Song>) : RecyclerView.Adapter<SongHolder>(), Observer {
    private lateinit var viewModelScope : CoroutineScope

    companion object : Subject {
        private val observers = mutableListOf<Observer>()

        override fun attachObserver(observer: Observer) {
            observers += observer
        }

        override fun detachObserver(observer: Observer) {
            observers -= observer
        }

        override fun notifyObserver() {
            for (observer in observers) {
                observer.update()
            }
        }
    }
    fun setViewModelScope(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SongHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSongBinding.inflate(inflater, parent, false)
        SongListViewModel.attachObserver(this)
        return SongHolder(binding, songs)
    }
    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val song = songs[position]
        Log.d("viewholder", "updating screen")
        holder.apply {
            binding.songUrl.text = song.songURL
            binding.imageUrl.text = song.imageURL
            Picasso.get().load(song.imageURL).placeholder(R.drawable.bill_up_close).into(binding.songImage)
            binding.songName.text = song.name
            binding.songArtist.text = song.artist
        }
        holder.bind(song)
    }
    override fun getItemCount() = songs.size
    override fun update() {
        viewModelScope.launch(Dispatchers.Main) {
            notifyDataSetChanged()
            Log.d("notification", "notified")
        }
    }
}