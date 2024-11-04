package com.example.carwash.Repository

import com.example.carwash.DAO.RegistroLavadoDAO
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.RegistroLavadoDetalles


class RegistroLavadoRepository(private val registrolavadoDAO: RegistroLavadoDAO) {
    suspend fun insertar(registrolavado: RegistroLavado) {
        return registrolavadoDAO.insertar(registrolavado)
    }

    suspend fun getAllRegistrosConDetalles(): List<RegistroLavadoDetalles> {
        return registrolavadoDAO.getAllRegistrosConDetalles()
    }

    suspend fun obtenerID(id: Int): RegistroLavado {
        return registrolavadoDAO.obtenerID(id)
    }

    suspend fun eliminar(id: Int): Int{
        return registrolavadoDAO.eliminar(id)
    }

    suspend fun actualizar(registrolavado: RegistroLavado) {
        return registrolavadoDAO.actualizar(registrolavado)
    }
}