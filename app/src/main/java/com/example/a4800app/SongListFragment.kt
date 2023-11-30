package com.example.a4800app

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4800app.databinding.FragmentSongListBinding

private const val TAG = "SongListFragment"

class SongListFragment : Fragment() {
    private var _binding: FragmentSongListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val songListViewModel: SongListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total songs: ${songListViewModel.playlist.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongListBinding.inflate(inflater, container, false)

        binding.songRecyclerView.layoutManager = LinearLayoutManager(context)

        val songs = songListViewModel.playlist
        val adapter = SongListAdapter(songs)
        binding.songRecyclerView.adapter = adapter

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}