package com.example.carwash.Repository

import com.example.carwash.DAO.RegistrolavadoDAO
import com.example.carwash.Models.RegistroLavado


class RegistroLavadoRepository(private val registrolavadoDAO: RegistrolavadoDAO) {
    suspend fun insertar(registrolavado: RegistroLavado) {
        return registrolavadoDAO.insertar(registrolavado)
    }

    suspend fun obtener(): List<RegistroLavado> {
        return registrolavadoDAO.obtenerTodos()
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