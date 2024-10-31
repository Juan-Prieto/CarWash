package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .padding(16.dp)
    ) {
        // Contenido sobre la imagen de fondo
        Column(
            modifier = Modifier
                .fillMaxSize(),
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

            // Botón de Registrarse con color de fondo Azul Marino y texto blanco
            Button(
                onClick = {
                    navController.navigate("register")
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF003366), // Azul Marino
                    contentColor = Color.White // Texto blanco
                )
            ) {
                Text("Registrarse")
            }

            // Botón de Historial con fondo blanco y borde y texto Azul Marino
            OutlinedButton(
                onClick = { navController.navigate("List") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF003366) // Texto Azul Marino
                ),
                border = BorderStroke(1.dp, Color(0xFF003366)) // Borde Azul Marino
            ) {
                Text("Historial de servicios")
            }
        }
    }
}
