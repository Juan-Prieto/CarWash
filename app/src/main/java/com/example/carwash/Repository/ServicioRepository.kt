package com.example.carwash.Repository

import com.example.carwash.DAO.ServicioDAO
import com.example.carwash.Models.Servicio

class ServicioRepository(private val servicioDAO: ServicioDAO){

    suspend fun insertar(servicio: Servicio) {
        return servicioDAO.insertar(servicio)
    }

    suspend fun obtener():List<Servicio>{
        return servicioDAO.obtenerTodos()
    }

    suspend fun eliminar(id : Int): Int{
        return servicioDAO.eliminar(id)
    }

    suspend fun actualizar(servicio : Servicio){
        return servicioDAO.actualizar(servicio)
    }

    suspend fun obtenerID(servicioId: Int): Servicio{
        return servicioDAO.obtenerID(servicioId)
    }

    suspend fun inicializarServicios(services: List<Servicio>) {
        val serviciosExistentes = servicioDAO.obtenerTodos()
        if (serviciosExistentes.isEmpty()) {
            services.forEach { servicio ->
                servicioDAO.insertar(servicio)
            }
        }
    }

    suspend fun borrarTodos() {
        servicioDAO.borrarTodos()
    }

}