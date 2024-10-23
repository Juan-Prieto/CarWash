package com.example.carwash.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val clienteID: Int = 0,
    val nombre:String,
    val apellido: String,
    val telefono: String,
    val email: String,
    val direccion: String,
    val contraseña: String
)