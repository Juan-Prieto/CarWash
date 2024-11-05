package com.example.carwash.ui.theme

import HomeScreen
import ServiceScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carwash.Repository.ClienteRepository
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository
import com.example.carwash.ui.theme.Screen.AddVehicleScreen
import com.example.carwash.ui.theme.Screen.CarWashScreen
import com.example.carwash.ui.theme.Screen.LoginScreen
import com.example.carwash.ui.theme.Screen.RegistrationForm
import com.example.carwash.ui.theme.Screen.RegistroListScreen
import com.example.carwash.ui.theme.Screen.SelectYourCarScreen
import com.example.carwash.ui.theme.Screen.SelectYourCarScreenThree
import com.example.carwash.ui.theme.Screen.SelectYourCarScreenTwo

@Composable
fun AppNavigation(
    clienteRepository: ClienteRepository,
    vehiculoRepository: VehiculoRepository,
    servicioRepository: ServicioRepository,
    registroLavadoRepository: RegistroLavadoRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "IntroductionOne") {

        composable("Introduction") { // Para hacer pruebas
            //LoginScreen()
            //RegistrationForm(navController = navController, clienteRepository = clienteRepository)
            //CarWashScreen()
        }

        composable("IntroductionOne") { // Primera pantalla
            SelectYourCarScreen(navController = navController)
        }

        composable("IntroductionTwo") { // Segunda pantalla
            SelectYourCarScreenTwo(navController = navController)
        }

        composable("IntroductionThree") { // Tercera pantalla
            SelectYourCarScreenThree(navController = navController)
        }

        composable("MainScreen") { // Pantalla Principal
            CarWashScreen(navController = navController)
        }

        composable("Register") { // Pantalla de Registro Cliente
            RegistrationForm(navController = navController, clienteRepository = clienteRepository)
        }

        composable("Login") { // Pantalla de iniciar sesión
            LoginScreen(navController = navController, clienteRepository = clienteRepository)
        }

        composable("HomeScreen/{clienteId}/{nombre}/{apellido}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "Nombre"
            val apellido = backStackEntry.arguments?.getString("apellido") ?: "Apellido"
            HomeScreen(
                navController = navController,
                clienteId = clienteId,
                vehiculoRepository = vehiculoRepository,
                registroLavadoRepository = registroLavadoRepository, // Asegúrate de pasar esto aquí
                nombre = nombre,
                apellido = apellido
            )
        }

        composable("AddVehicle/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            AddVehicleScreen(navController = navController, clienteId = clienteId, vehiculoRepository = vehiculoRepository)
        }

        composable("ServiceScreen/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            ServiceScreen(
                clienteId = clienteId,
                vehiculoRepository = vehiculoRepository,
                servicioRepository = servicioRepository,
                registroLavadoRepository = registroLavadoRepository,
                navController = navController
            )
        }

        composable("RegistroListScreen/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            RegistroListScreen(
                registroLavadoRepository = registroLavadoRepository,
                navController = navController
            )
        }


    }
}

