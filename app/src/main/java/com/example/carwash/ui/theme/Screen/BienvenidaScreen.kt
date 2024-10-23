package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.R

@Composable
fun BienvenidaScreen(navController: NavController) {
    // Contenedor principal
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Imagen de fondo",
            contentScale = ContentScale.Crop, // Ajusta la imagen para que cubra toda la pantalla
            modifier = Modifier.fillMaxSize()
        )

        // Contenido sobre la imagen de fondo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Espacio para el logo con esquinas redondeadas
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logotipo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .clip(RoundedCornerShape(5.dp)) // Bordes redondeados
            )

            // Texto centrado
            Text(
                text = "Bienvenido a nuestro autolavado CarWash",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            // Card con el texto justificado
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF20244A).copy(alpha = 0.85f) // Color que combina con el fondo
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp // Resalte de la Card
                ),
                shape = RoundedCornerShape(8.dp) // Bordes redondeados en la Card
            ) {
                Text(
                    text = "Si ya tienes cuenta, pulsa el botón iniciar sesión para solicitar uno de nuestros servicios. Si es tu primera vez ingresando, pulsa el botón registrarse para disfrutar y solicitar de nuestros servicios.",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Box con los botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Registrar lavado")
                }
                Button(
                    onClick = {
                        navController.navigate("register")
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Registrarse")
                }
            }

            // Imagen de publicidad con esquinas redondeadas
            Image(
                painter = painterResource(id = R.drawable.publicidad),
                contentDescription = "Publicidad",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .clip(RoundedCornerShape(5.dp)) // Bordes redondeados
            )
        }
    }
}


