package com.example.carwash.Models

import androidx.room.Embedded
import androidx.room.Relation

data class ClienteVehiculo(
    @Embedded val cliente: Cliente,

    @Relation(
        parentColumn = "clienteID",
        entityColumn = "clienteId"
    )
    val vehiculos: List<Vehiculo>
)