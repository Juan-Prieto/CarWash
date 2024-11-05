package com.example.carwash.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.carwash.Models.Vehiculo

@Dao
interface VehiculoDAO {

    @Query("SELECT * FROM vehiculos")
    suspend fun obtenerTodos(): List<Vehiculo>

    @Query("SELECT * FROM vehiculos WHERE vehiculoID = :vehiculoID")
    suspend fun obtenerID(vehiculoID : Int): Vehiculo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(vehiculo:Vehiculo): Long

    @Query("DELETE FROM vehiculos WHERE vehiculoID = :vehiculoID")
    suspend fun eliminar(vehiculoID: Int): Int

    @Update
    suspend fun actualizar(vehiculo: Vehiculo)

    @Query("SELECT * FROM vehiculos WHERE clienteId = :clienteId")
    suspend fun obtenerVehiculosPorCliente(clienteId: Int): List<Vehiculo>

    @Query("SELECT * FROM vehiculos")
    suspend fun obtener(): List<Vehiculo>
}