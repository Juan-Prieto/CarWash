package com.example.carwash.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.RegistroLavadoDetalles

@Dao
interface RegistrolavadoDAO {

    @Query("SELECT * FROM registroslavado")
    suspend fun obtenerTodos(): List<RegistroLavado>

    @Query("SELECT * FROM registroslavado WHERE registroID = :registroId")
    suspend fun obtenerID(registroId: Int): RegistroLavado

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(registro: RegistroLavado)

    @Query("DELETE FROM registroslavado WHERE registroID = :registroId")
    suspend fun eliminar(registroId: Int): Int

    @Update
    suspend fun actualizar(registroLavado: RegistroLavado)

    @Transaction
    @Query("SELECT * FROM registroslavado WHERE registroID = :registroID")
    fun getRegistroLavadoConDetalles(registroID: Int): LiveData<RegistroLavadoDetalles>
}