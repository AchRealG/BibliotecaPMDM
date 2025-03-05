package com.example.bibliotecapmdm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bibliotecapmdm.Entities.Book
import com.example.bibliotecapmdm.R
import com.example.bibliotecapmdm.databinding.ItemBookBinding

class BookAdapter(private val bookList: List<Book>, private val onClick: (Book) -> Unit ,
                  private val onLongClick: (Book) -> Unit,
                  private val onUpdateClick: (Book) -> Unit,
                  private val onFavoriteClick: (Book) -> Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = bookList.size

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            val ivImagen = binding.bookImage
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookDescription.text = book.description

            Glide.with(ivImagen.context)
                .load(book.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivImagen)

            binding.root.setOnClickListener { onClick(book) }

            // Click prolongado para eliminar el libro
            binding.root.setOnLongClickListener {
                onLongClick(book)
                true
            }

            // Botón para actualizar el libro
            binding.btnUpdate.setOnClickListener { onUpdateClick(book) }

            // Botón para marcar/desmarcar como favorito
            binding.btnLike.setOnClickListener { onFavoriteClick(book) }

            // Mostrar el estado favorito según la propiedad esFavorita del libro
            if (book.esFavorita) {
                binding.btnLike.setBackgroundResource(R.drawable.ic_favorite_filled)
            } else {
                binding.btnLike.setBackgroundResource(R.drawable.ic_favorite_border)
            }
        }
    }
}