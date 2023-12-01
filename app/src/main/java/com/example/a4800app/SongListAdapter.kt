package com.example.a4800app

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4800app.databinding.ListItemSongBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SongHolder(val binding: ListItemSongBinding) : RecyclerView.ViewHolder(binding.root) {

}

class SongListAdapter(private val songs: List<Song>) : RecyclerView.Adapter<SongHolder>(), Observer {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SongHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSongBinding.inflate(inflater, parent, false)
        SongListViewModel.attachObserver(this)
        return SongHolder(binding)
    }
    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val song = songs[position]
        Log.d("viewholder", "updating screen")
        holder.apply {
            Picasso.get().load(song.imageURL).placeholder(R.drawable.bill_up_close).into(binding.songImage)
            binding.songName.text = song.name
            binding.songArtist.text = song.artist
        }
    }
    override fun getItemCount() = songs.size
    override fun update() {
        GlobalScope.launch(Dispatchers.Main) {
            notifyDataSetChanged()
            Log.d("notification", "notified")
        }
    }
}