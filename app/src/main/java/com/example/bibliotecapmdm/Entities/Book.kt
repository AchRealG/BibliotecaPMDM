package com.example.bibliotecapmdm.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String = "",
    var esFavorita: Boolean = false
) : Serializable