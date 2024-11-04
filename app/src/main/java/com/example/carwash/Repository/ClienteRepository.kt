package com.example.carwash.Repository

import com.example.carwash.DAO.ClienteDAO
import com.example.carwash.Models.Cliente

class ClienteRepository(private val clienteDAO: ClienteDAO){

    suspend fun insertar(cliente: Cliente): Long {
        return clienteDAO.insertar(cliente)
    }

    suspend fun obtenerTodos(): List<Cliente> {
        return clienteDAO.obtenerTodos()
    }

    suspend fun obtenerID(id: Int): Cliente {
        return clienteDAO.obtenerID(id)
    }

    suspend fun eliminar(id : Int): Int {
        return clienteDAO.eliminar(id)
    }

    suspend fun actualizar(cliente : Cliente) {
        return clienteDAO.actualizar(cliente)
    }

    suspend fun iniciarSesion(telefono: String, contraseña: String): Cliente? {
        return clienteDAO.iniciarSesion(telefono, contraseña)
    }

}