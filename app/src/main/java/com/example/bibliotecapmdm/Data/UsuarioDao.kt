package com.example.bibliotecapmdm.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bibliotecapmdm.Entities.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert
    suspend fun agregarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM UsuarioEntity WHERE email = :email")
    suspend fun obtenerUsuario(email: String): UsuarioEntity?
}