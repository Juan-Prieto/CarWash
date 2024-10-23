package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.Vehiculo
import com.example.carwash.R
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.launch

@Composable
fun RegistroVehiculoScreen(
    navController: NavController,
    clienteId: Int, // ID del cliente que se está registrando
    vehiculoRepository: VehiculoRepository
) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Text(
                text = "Registrar Vehículo",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF20244A).copy(alpha = 0.85f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            ) {
                Text(
                    text = "Ingresa los datos de tu vehículo y selecciona el servicio que deseas.",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            TextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            TextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            TextField(
                value = placa,
                onValueChange = { placa = it },
                label = { Text("Placa") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            TextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            TextField(
                value = tipo,
                onValueChange = { tipo = it },
                label = { Text("Tipo de Vehículo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    if (marca.isNotEmpty() && modelo.isNotEmpty() && placa.isNotEmpty() && color.isNotEmpty() && tipo.isNotEmpty()) {
                        scope.launch {
                            val vehiculoID = vehiculoRepository.insertar(
                                Vehiculo(
                                    clienteId = clienteId,
                                    marca = marca,
                                    modelo = modelo,
                                    placa = placa,
                                    color = color,
                                    tipo = tipo
                                )
                            )
                            navController.navigate("services/$vehiculoID")
                        }
                    } else {
                        message = "Por favor, completa todos los campos."
                    }

                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Registrar Vehículo")
            }

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
