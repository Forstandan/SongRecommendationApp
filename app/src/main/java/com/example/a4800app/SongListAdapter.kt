package com.example.a4800app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a4800app.databinding.ListItemSongBinding
import com.squareup.picasso.Picasso

class SongHolder(
    val binding: ListItemSongBinding
) : RecyclerView.ViewHolder(binding.root) {

}

class SongListAdapter(
    private val songs: List<Song>
) : RecyclerView.Adapter<SongHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : SongHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSongBinding.inflate(inflater, parent, false)
        return SongHolder(binding)
    }
    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val song = songs[position]
        holder.apply {
            Picasso.get().load(song.imageURL).placeholder(R.drawable.bill_up_close).into(binding.songImage)
            binding.songName.text = song.name
            binding.songArtist.text = song.artist
        }
    }
    override fun getItemCount() = songs.size
}