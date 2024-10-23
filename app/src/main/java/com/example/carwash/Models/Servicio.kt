package com.example.carwash.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Servicios")
data class Servicio(
    @PrimaryKey(autoGenerate = true) val servicioID: Int = 0,
    val nombre: String,
    val precio: Double
)