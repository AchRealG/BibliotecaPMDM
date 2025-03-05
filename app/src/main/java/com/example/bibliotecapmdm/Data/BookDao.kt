package com.example.bibliotecapmdm.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.bibliotecapmdm.Entities.Book

@Dao
interface BookDao {
    @Insert
    suspend fun addBook(book: Book)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book?

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}