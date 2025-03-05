package com.example.bibliotecapmdm.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bibliotecapmdm.Entities.Book
import com.example.bibliotecapmdm.Entities.UsuarioEntity

@Database(entities = [Book::class,UsuarioEntity::class] ,version = 2)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun usuarioDao(): UsuarioDao


    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}