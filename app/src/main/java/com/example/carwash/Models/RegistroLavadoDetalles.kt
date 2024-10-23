package com.example.carwash.Models

import androidx.room.Embedded
import androidx.room.Relation

data class RegistroLavadoDetalles(
    @Embedded val registroLavado: RegistroLavado,

    @Relation(
        parentColumn = "vehiculoId",
        entityColumn = "vehiculoID"
    )
    val vehiculo: Vehiculo,

    @Relation(
        parentColumn = "servicioId",
        entityColumn = "servicioID"
    )
    val servicio: Servicio
)