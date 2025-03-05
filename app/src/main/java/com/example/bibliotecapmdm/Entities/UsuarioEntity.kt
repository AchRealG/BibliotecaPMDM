package com.example.bibliotecapmdm.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "UsuarioEntity")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String?,
    val email: String?,
    val contrasena: String?
) : Serializable
