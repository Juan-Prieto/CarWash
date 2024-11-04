package com.example.carwash.Models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Vehiculos")
data class Vehiculo(
    @PrimaryKey(autoGenerate = true) val vehiculoID: Int = 0,
    val clienteId: Int,
    val marca: String, //
    val modelo: String, //
    val placa: String, //
    val color: String, //
    val tipo: String //
)