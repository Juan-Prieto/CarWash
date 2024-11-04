package com.example.carwash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.carwash.DAO.ClienteDAO
import com.example.carwash.DAO.RegistroLavadoDAO
import com.example.carwash.DAO.ServicioDAO
import com.example.carwash.DAO.VehiculoDAO
import com.example.carwash.DataBase.AppDatabase
import com.example.carwash.Repository.ClienteRepository
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository
import com.example.carwash.ui.theme.AppNavigation

class MainActivity : ComponentActivity() {

    private lateinit var clienteDAO: ClienteDAO
    private lateinit var registroLavadoDAO: RegistroLavadoDAO
    private lateinit var servicioDAO: ServicioDAO
    private lateinit var vehiculoDAO: VehiculoDAO

    private lateinit var clienteRepository: ClienteRepository
    private lateinit var registroLavadoRepository: RegistroLavadoRepository
    private lateinit var servicioRepository: ServicioRepository
    private lateinit var vehiculoRepository: VehiculoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(applicationContext)

        clienteDAO = db.clienteDAO()
        registroLavadoDAO = db.registroLavadoDAO()
        servicioDAO = db.servicioDAO()
        vehiculoDAO = db.vehiculoDAO()

        clienteRepository = ClienteRepository(clienteDAO)
        registroLavadoRepository = RegistroLavadoRepository(registroLavadoDAO)
        servicioRepository = ServicioRepository(servicioDAO)
        vehiculoRepository = VehiculoRepository(vehiculoDAO)

        setContent {
            AppNavigation(
                clienteRepository = clienteRepository,
                vehiculoRepository = vehiculoRepository,
                servicioRepository = servicioRepository,
                registroLavadoRepository = registroLavadoRepository
            )
        }
    }
}
