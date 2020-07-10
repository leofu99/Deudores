package com.example.sesionroom.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_usuario")
class Usuario(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "correo") val correo: String,
    @ColumnInfo(name = "contraseña") val contrasena: String
)