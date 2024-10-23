package com.example.carwash.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carwash.Repository.ClienteRepository
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository
import com.example.carwash.ui.theme.Screen.BienvenidaScreen
import com.example.carwash.ui.theme.Screen.RegisterScreen
import com.example.carwash.ui.theme.Screen.RegistroVehiculoScreen
import com.example.carwash.ui.theme.Screen.ServiceSelectionScreen

@Composable
fun AppNavigation(
    clienteRepository: ClienteRepository,
    registroLavadoRepository: RegistroLavadoRepository,
    vehiculoRepository: VehiculoRepository,
    servicioRepository: ServicioRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        // Pantalla de bienvenida
        composable("welcome") {
            BienvenidaScreen(navController = navController)
        }

        // Pantalla de registro de cliente
        composable("register") {
            RegisterScreen(
                navController = navController,
                clienteRepository = clienteRepository
            )
        }

        composable(
            "registrovehiculo/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            RegistroVehiculoScreen(
                navController = navController,
                clienteId = clienteId,
                vehiculoRepository = vehiculoRepository
            )
        }

        composable("services/{vehiculoId}") { backStackEntry ->
            val vehiculoId = backStackEntry.arguments?.getString("vehiculoId")?.toInt() ?: 0
            ServiceSelectionScreen(
                navController = navController,
                vehiculoId = vehiculoId,
                servicioRepository = servicioRepository,
                registroLavadoRepository = registroLavadoRepository
            )
        }

        // Otras pantallas se pueden agregar aquí
    }
}

