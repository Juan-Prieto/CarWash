package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.carwash.R

@Composable
fun CarWashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0969E6), // Azul más oscuro
                        Color(0xFF2193DF)  // Azul más claro
                    )
                )
            ) // Fondo azul marino
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Reemplaza con tu recurso de logo
            contentDescription = "Logo de Wash",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillHeight,
            alpha = 0.2f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Logo
            Image(
                painter = painterResource(id = R.drawable.logo), // Reemplaza con tu recurso de logo
                contentDescription = "Logo de Wash",
                modifier = Modifier
            )

            // Eslogan
            Text(
                text = "Clean your car anywhere, at any time.",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.W300,

            )

            Spacer(modifier =   Modifier.height(50.dp))

            // Botón de crear cuenta
            Button(
                onClick = {
                    navController.navigate("Register")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Create Account",
                    color = Color(0xFF0969E6),
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón de iniciar sesión
            OutlinedButton(
                onClick = {
                    navController.navigate("Login")
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White
                )
            }
        }
    }
}



