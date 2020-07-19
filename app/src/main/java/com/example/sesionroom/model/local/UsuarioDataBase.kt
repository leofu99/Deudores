package com.example.sesionroom.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sesionroom.model.local.Usuario
import com.example.sesionroom.model.local.UsuarioDAO

@Database(entities = arrayOf(Usuario::class),version = 1)
abstract class UsuarioDataBase:RoomDatabase() {
    abstract fun UsuarioDAO() : UsuarioDAO
}
