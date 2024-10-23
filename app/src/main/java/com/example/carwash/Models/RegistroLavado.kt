package com.example.carwash.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RegistrosLavado")
data class RegistroLavado(
    @PrimaryKey(autoGenerate = true) val registroID: Int = 0,
    val vehiculoId: Int,
    val servicioId: Int,
    val fechaLavado: String,
    val horaInicio: String,
    val horaFin: String,
    val precioTotal: Double
)