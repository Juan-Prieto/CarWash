package com.example.carwash.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carwash.Repository.ClienteRepository
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository
import com.example.carwash.ui.theme.Screen.AddVehicleScreen
import com.example.carwash.ui.theme.Screen.BienvenidaScreen
import com.example.carwash.ui.theme.Screen.CarWashRequestScreen
import com.example.carwash.ui.theme.Screen.CarWashScreen
import com.example.carwash.ui.theme.Screen.LoginScreen
import com.example.carwash.ui.theme.Screen.RegisterScreen
import com.example.carwash.ui.theme.Screen.RegistrationForm
import com.example.carwash.ui.theme.Screen.RegistroListScreen
import com.example.carwash.ui.theme.Screen.RegistroVehiculoScreen
import com.example.carwash.ui.theme.Screen.SelectYourCarScreen
import com.example.carwash.ui.theme.Screen.SelectYourCarScreenThree
import com.example.carwash.ui.theme.Screen.SelectYourCarScreenTwo
import com.example.carwash.ui.theme.Screen.ServiceSelectionScreen

@Composable
fun AppNavigation(
    clienteRepository: ClienteRepository,
    vehiculoRepository: VehiculoRepository,
    servicioRepository: ServicioRepository,
    registroLavadoRepository: RegistroLavadoRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "MainScreen") {

        composable("Introduction"){ // Para hacer pruebas
            //LoginScreen()
            //RegistrationForm(navController = navController, clienteRepository = clienteRepository)
            //CarWashScreen()
        }

        composable("IntroductionOne"){ // Primera pantalla
            SelectYourCarScreen(navController = navController)
        }

        composable("IntroductionTwo") { // Segunda pantalla
            SelectYourCarScreenTwo(navController = navController)
        }

        composable("IntroductionThree"){ // Tercera pantalla
            SelectYourCarScreenThree(navController = navController)
        }

        composable("MainScreen"){ // Pantalla Principal
            CarWashScreen(navController = navController)
        }

        composable("Register") { // Pantalla de Registro Cliente
            RegistrationForm(navController = navController, clienteRepository = clienteRepository)
        }

        composable("Login") { // Pantalla de iniciar sesión
            LoginScreen(navController = navController, clienteRepository = clienteRepository)
        }

        composable("AddVehicle/{clienteId}"){ backStackEntry ->// Pantalla para ingresar vehiculo
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            AddVehicleScreen(clienteId = clienteId, navController = navController, vehiculoRepository = vehiculoRepository)
        }

        composable("List") {
            RegistroListScreen(
                navController = navController,
                registroLavadoRepository = registroLavadoRepository
            )
        }

        composable("registrovehiculo/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            RegistroVehiculoScreen(
                navController = navController,
                vehiculoRepository = vehiculoRepository,
                clienteId = clienteId
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

        composable("IntroductionOne"){ // Primera pantalla
            SelectYourCarScreen(navController = navController)
        }

        composable("Services"){
            CarWashRequestScreen(vehiculoRepository = vehiculoRepository)
        }

    }
}

