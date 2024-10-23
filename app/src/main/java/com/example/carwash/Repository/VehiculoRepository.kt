package com.example.carwash.Repository

import com.example.carwash.DAO.VehiculoDAO
import com.example.carwash.Models.Vehiculo

class VehiculoRepository (private val vehiculoDAO: VehiculoDAO){
    suspend fun insertar(vehiculo: Vehiculo): Long{
        return vehiculoDAO.insertar(vehiculo)
    }

    suspend fun obtener():List<Vehiculo>{
        return vehiculoDAO.obtenerTodos()
    }

    suspend fun obtenerID(vehiculoId: Int): Vehiculo?{
        return vehiculoDAO.obtenerID(vehiculoId)
    }

    suspend fun eliminar(id : Int): Int{
        return vehiculoDAO.eliminar(id)
    }

    suspend fun actualizar(vehiculo : Vehiculo){
        return vehiculoDAO.actualizar(vehiculo)
    }

}