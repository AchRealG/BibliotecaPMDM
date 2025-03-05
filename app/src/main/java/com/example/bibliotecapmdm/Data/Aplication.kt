package com.example.bibliotecapmdm.Data

import android.app.Application
import androidx.room.Room
import com.example.bibliotecapmdm.Entities.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Aplication : Application() {
    companion object {
        lateinit var baseDeDatos: BookDatabase
    }

    override fun onCreate() {
        super.onCreate()

        // Inicializamos la base de datos con Room
        baseDeDatos = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "book_database"
        )
            .fallbackToDestructiveMigration()
            .build()

        agregarLibros()
    }

    private fun agregarLibros() {
        CoroutineScope(Dispatchers.IO).launch {
            val librosObtenidos = baseDeDatos.bookDao().getAllBooks()
            if (librosObtenidos.isEmpty()) {
                try {
                    val libros = listOf(
                        Book(
                            title = "Cien años de soledad",
                            author = "Gabriel García Márquez",
                            description = "Una obra maestra del realismo mágico que narra la historia de la familia Buendía.",
                            imageUrl = "http://librosrecomendados10.com/wp-content/uploads/2009/09/cien.gif",
                            esFavorita = false
                        ),
                        Book(
                            title = "1984",
                            author = "George Orwell",
                            description = "Una novela distópica que explora temas de vigilancia y totalitarismo.",
                            imageUrl = "https://http2.mlstatic.com/D_NQ_NP_860751-MLU70838217346_082023-O.webp",
                            esFavorita = false
                        ),
                        Book(
                            title = "El Quijote",
                            author = "Miguel de Cervantes",
                            description = "La clásica historia de las aventuras de Don Quijote y su escudero Sancho.",
                            imageUrl = "https://m.media-amazon.com/images/I/61HOpyVqSeL.jpg",
                            esFavorita = false
                        ),
                        Book(
                            title = "La sombra del viento",
                            author = "Carlos Ruiz Zafón",
                            description = "Un misterio literario ambientado en la Barcelona de posguerra.",
                            imageUrl = "https://2.bp.blogspot.com/-IF_jkDt--Ag/VaeTw2mctqI/AAAAAAAAAOs/wHmEC9TZ9xY/s1600/PORTADA.png",
                            esFavorita = false
                        )
                    )
                    libros.forEach { libro ->
                        baseDeDatos.bookDao().addBook(libro)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}