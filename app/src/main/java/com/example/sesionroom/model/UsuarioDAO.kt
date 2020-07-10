package com.example.sesionroom.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDAO {
    @Insert
    fun crearUsuario(usuario: Usuario)

    @Query
        ("SELECT * FROM tabla_usuario WHERE  correo LIKE :correo")
    fun buscarUsuario(correo: String) : Usuario

}