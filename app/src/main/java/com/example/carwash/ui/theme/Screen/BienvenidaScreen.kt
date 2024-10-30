package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carwash.R

@Composable
fun BienvenidaScreen(navController: NavController) {
    // Contenedor principal
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Contenido sobre la imagen de fondo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .padding(10.dp)
            )

            Button(
                onClick = {
                    navController.navigate("register")
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text("Registrar")
            }

            OutlinedButton(
                onClick = { navController.navigate("List") },

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            {
                Text("Historial de servicios")
            }
        }
    }
}


