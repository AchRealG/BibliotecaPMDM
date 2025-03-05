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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bibliotecapmdm.Data.Aplication
import com.example.bibliotecapmdm.Entities.UsuarioEntity
import com.example.bibliotecapmdm.R
import com.example.bibliotecapmdm.databinding.FragmentBookListBinding
import com.example.bibliotecapmdm.Adapter.BookAdapter
import com.example.bibliotecapmdm.Entities.Book
import kotlinx.coroutines.launch

class BookListFragment : Fragment(R.layout.fragment_book_list) {

    private lateinit var binding: FragmentBookListBinding
    private var usuario: UsuarioEntity? = null
    private lateinit var adapter: BookAdapter
    private var bookList: MutableList<Book> = mutableListOf()
    private lateinit var mediaPlayer: MediaPlayer
    private var musicaPosicion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuario = BookListFragmentArgs.fromBundle(it).Usuario
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
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




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Instanciar el adaptador con todas las funciones implementadas:
        adapter = BookAdapter(
            bookList = bookList,
            onClick = { book ->
                val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(book)
                findNavController().navigate(action)
            },
            onLongClick = { book ->
                // Acción para click prolongado: eliminar el libro
                val index = bookList.indexOf(book)
                if (index != -1) {
                    bookList.removeAt(index)
                    adapter.notifyItemRemoved(index)
                    Toast.makeText(requireContext(), "Libro eliminado", Toast.LENGTH_SHORT).show()
                    // Aquí podrías eliminarlo también de la base de datos, si aplica
                }
            },
            onUpdateClick = { book ->
                // Acción para actualizar el libro: abrir un diálogo o modificar directamente
                // Aquí se simula actualizando el título agregando " - Actualizado"
                val index = bookList.indexOf(book)
                if (index != -1) {
                    val updatedBook = book.copy(title = book.title + " - Actualizado")
                    bookList[index] = updatedBook
                    adapter.notifyItemChanged(index)
                    Toast.makeText(requireContext(), "Libro actualizado", Toast.LENGTH_SHORT).show()
                    // Aquí puedes actualizar la base de datos con el cambio
                }
            },
            onFavoriteClick = { book ->
                // Acción para marcar/desmarcar favorito y actualizar la vista
                val index = bookList.indexOf(book)
                if (index != -1) {
                    book.esFavorita = !book.esFavorita
                    adapter.notifyItemChanged(index)
                    val msg = if (book.esFavorita) "Libro marcado como favorito" else "Libro desmarcado"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    // Aquí actualiza la base de datos si fuera necesario
                }
            }
        )
            binding.recyclerView.adapter = adapter

            // Cargar libros desde la base de datos
            loadBooksFromDb()
        }

    private fun loadBooksFromDb() {
        lifecycleScope.launch {
            val booksFromDb = Aplication.baseDeDatos.bookDao().getAllBooks()
            bookList.clear()
            bookList.addAll(booksFromDb)
            adapter.notifyDataSetChanged()
        }
    }

    // También refrescar la lista en onResume para detectar cambios recientes
    override fun onResume() {
        super.onResume()
        if (mediaPlayer != null && !mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(musicaPosicion)
            mediaPlayer.start()
        }
        loadBooksFromDb()
    }
}
