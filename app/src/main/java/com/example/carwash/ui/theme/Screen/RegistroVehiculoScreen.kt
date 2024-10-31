package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.Vehiculo
import com.example.carwash.R
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    var mensajeError by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Validadores para cada campo
    val validarMarcaModelo: (String) -> Boolean = { it.matches(Regex("^[a-zA-Z0-9\\s]*$")) }
    val validarPlaca: (String) -> Boolean = { it.matches(Regex("^[A-Z0-9-]*$")) }
    val validarColorTipo: (String) -> Boolean = { it.matches(Regex("^[a-zA-Z\\s]*$")) }

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
                    .padding(16.dp)
            )

            Text(
                text = "Registrar Vehículo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Thin,
                color = Color(0xFF070707),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campos de entrada con validación
            OutlinedTextField(
                value = marca,
                onValueChange = { if (validarMarcaModelo(it)) marca = it },
                label = { Text("Marca", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = modelo,
                onValueChange = { if (validarMarcaModelo(it)) modelo = it },
                label = { Text("Modelo", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = placa,
                onValueChange = { if (validarPlaca(it)) placa = it },
                label = { Text("Placa", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = color,
                onValueChange = { if (validarColorTipo(it)) color = it },
                label = { Text("Color", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                value = tipo,
                onValueChange = { if (validarColorTipo(it)) tipo = it },
                label = { Text("Tipo de Vehículo", fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Botón para registrar el vehículo
            Button(
                onClick = {
                    if (marca.isNotBlank() && modelo.isNotBlank() && placa.isNotBlank()
                        && color.isNotBlank() && tipo.isNotBlank()
                    ) {
                        scope.launch {
                            vehiculoRepository.insertar(
                                Vehiculo(
                                    clienteId = clienteId,
                                    marca = marca,
                                    modelo = modelo,
                                    placa = placa,
                                    color = color,
                                    tipo = tipo
                                )
                            )
                            withContext(Dispatchers.Main) {
                                navController.navigate("services/$clienteId")
                            }
                        }
                    } else {
                        mensajeError = "Todos los campos son obligatorios."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp)
            ) {
                Text("Registrar Vehículo", fontSize = 15.sp)
            }
        }
    }
}
