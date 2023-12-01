package com.example.a4800app

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a4800app.databinding.FragmentSongListBinding

private const val TAG = "SongListFragment"

class SongListFragment : Fragment(R.layout.fragment_song_list) {
    private var _binding: FragmentSongListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val songListViewModel: SongListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total songs: ${songListViewModel.songs.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSongListBinding.inflate(inflater, container, false)

        binding.songRecyclerView.layoutManager = LinearLayoutManager(context)

        val songs = songListViewModel.songs
        val adapter = SongListAdapter(songs)
        binding.songRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_song_list, menu)

                val searchItem: MenuItem = menu.findItem(R.id.app_bar_search)
                val searchView = searchItem.actionView as? SearchView
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        Log.d(TAG, "QueryTextSubmit: $query")
                        songListViewModel.setQuery(query ?: "")
                        return true
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {
                        Log.d(TAG, "QueryTextChange: $newText")
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }

        }, viewLifecycleOwner)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}