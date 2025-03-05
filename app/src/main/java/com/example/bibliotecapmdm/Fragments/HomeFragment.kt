package com.example.bibliotecapmdm.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bibliotecapmdm.Entities.UsuarioEntity
import com.example.bibliotecapmdm.R
import com.example.bibliotecapmdm.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var usuario: UsuarioEntity? = null
    private lateinit var mediaPlayer: MediaPlayer
    private var musicaPosicion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuario = HomeFragmentArgs.fromBundle(it).Usuario
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.musica)
        mediaPlayer.isLooping = true
        mediaPlayer.seekTo(musicaPosicion)
        mediaPlayer.start()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        musicaPosicion = mediaPlayer.currentPosition
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer != null && !mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(musicaPosicion)
            mediaPlayer.start()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnBookList.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBookListFragment(usuario))
        }
        binding.btnAddBook.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddBookFragment(usuario))
        }
    }
}