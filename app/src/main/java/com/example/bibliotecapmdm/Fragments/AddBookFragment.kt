package com.example.bibliotecapmdm.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bibliotecapmdm.Data.Aplication
import com.example.bibliotecapmdm.Entities.Book
import com.example.bibliotecapmdm.Entities.UsuarioEntity
import com.example.bibliotecapmdm.R
import com.example.bibliotecapmdm.databinding.FragmentAddBookBinding
import kotlinx.coroutines.launch

class AddBookFragment : Fragment(R.layout.fragment_add_book) {

    private lateinit var binding: FragmentAddBookBinding
    private var usuario: UsuarioEntity? = null
    private lateinit var mediaPlayer: MediaPlayer
    private var musicaPosicion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuario = AddBookFragmentArgs.fromBundle(it).Usuario
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddBookBinding.inflate(inflater, container, false)
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
        binding.btnAddBook.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val author = binding.etAuthor.text.toString()
            val description = binding.etDescription.text.toString()
            val imageUrl = binding.etImageUrl.text.toString()

            if (title.isNotEmpty() && author.isNotEmpty() && description.isNotEmpty() && imageUrl.isNotEmpty()) {
                val newBook = Book(title = title, author = author, description = description, imageUrl = imageUrl)
                lifecycleScope.launch {
                    Aplication.baseDeDatos.bookDao().addBook(newBook)
                    Toast.makeText(requireContext(), "Libro agregado", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(AddBookFragmentDirections.actionAddBookFragmentToHomeFragment(usuario))
                }
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
