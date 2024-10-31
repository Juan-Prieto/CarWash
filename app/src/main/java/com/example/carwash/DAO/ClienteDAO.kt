package com.example.carwash.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.carwash.Models.Cliente
import com.example.carwash.Models.ClienteVehiculo
import com.example.carwash.Models.Vehiculo

@Dao
interface ClienteDAO {

    @Query("SELECT * FROM Clientes")
    suspend fun obtenerTodos(): List<Cliente>

    @Query("SELECT * FROM Clientes WHERE clienteID = :id")
    suspend fun obtenerID(id: Int): Cliente

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(cliente: Cliente): Long

    @Query("DELETE FROM Clientes WHERE clienteID = :clienteID")
    suspend fun eliminar(clienteID: Int): Int

    @Update
    suspend fun actualizar(cliente: Cliente)

//    @Transaction
//    @Query("SELECT * FROM Vehiculos WHERE clienteID = :clienteID")
//    suspend fun obtenerVehiculosCliente(clienteID: Int): List<Vehiculo>

    @Transaction
    @Query("SELECT * FROM clientes")
    fun getAllClientesConVehiculos(): LiveData<List<ClienteVehiculo>>
}
