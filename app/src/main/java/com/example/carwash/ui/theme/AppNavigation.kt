package com.example.carwash.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carwash.Repository.ClienteRepository
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository
import com.example.carwash.ui.theme.Screen.AddVehicleScreen
import com.example.carwash.ui.theme.Screen.BienvenidaScreen
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

    NavHost(navController = navController, startDestination = "IntroductionOne") {

        composable("IntroductionOne"){
            //SelectYourCarScreen(navController = navController)
            //LoginScreen()
            //RegistrationForm()
            //CarWashScreen()
            AddVehicleScreen()
        }

        composable("IntroductionTwo"){
            SelectYourCarScreenTwo(navController = navController)
        }

        composable("IntroductionThree"){
            SelectYourCarScreenThree(navController = navController)
        }

        composable("Login") {
            BienvenidaScreen(navController = navController)
        }

        composable("List") {
            RegistroListScreen(
                navController = navController,
                registroLavadoRepository = registroLavadoRepository
            )
        }

        composable("register") {
            RegisterScreen(navController = navController, clienteRepository = clienteRepository)
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

    }
}

