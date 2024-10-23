package com.example.carwash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.carwash.DAO.ClienteDAO
import com.example.carwash.DAO.RegistrolavadoDAO
import com.example.carwash.DAO.ServicioDAO
import com.example.carwash.DAO.VehiculoDAO
import com.example.carwash.DataBase.AppDatabase
import com.example.carwash.Repository.ClienteRepository
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository
import com.example.carwash.ui.theme.AppNavigation

class MainActivity : ComponentActivity() {

    private lateinit var ClienteDAO: ClienteDAO
    private lateinit var RegistrolavadoDAO: RegistrolavadoDAO
    private lateinit var ServicioDAO: ServicioDAO
    private lateinit var VehiculoDAO: VehiculoDAO

    private lateinit var clienteRepository: ClienteRepository
    private lateinit var registroLavadoRepository: RegistroLavadoRepository
    private lateinit var ServicioRepository: ServicioRepository
    private lateinit var VehiculoRepository: VehiculoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(applicationContext)

        ClienteDAO = db.clienteDAO()
        RegistrolavadoDAO = db.registroLavadoDAO()
        ServicioDAO = db.servicioDAO()
        VehiculoDAO = db.vehiculoDAO()

        clienteRepository = ClienteRepository(ClienteDAO)
        registroLavadoRepository = RegistroLavadoRepository(RegistrolavadoDAO)
        ServicioRepository = ServicioRepository(ServicioDAO)
        VehiculoRepository = VehiculoRepository(VehiculoDAO)

        setContent {
            AppNavigation(
                clienteRepository = clienteRepository,
                vehiculoRepository = VehiculoRepository,
                servicioRepository = ServicioRepository,
                registroLavadoRepository = registroLavadoRepository
            )
        }
    }
}
