package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.Servicio
import com.example.carwash.R
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import kotlinx.coroutines.launch

@Composable
fun ServiceSelectionScreen(
    navController: NavController,
    vehiculoId: Int,
    servicioRepository: ServicioRepository,
    registroLavadaRepository: RegistroLavadoRepository
) {
    val servicios = listOf(
        "Lavado Exterior" to 10000.0,
        "Lavado Interior" to 15000.0,
        "Lavado Full" to 35000.0,
        "Lavado Premium" to 60000.0
    )

    val selectedServices = remember { mutableStateListOf<Pair<String, Double>>() }
    var totalPrice by remember { mutableStateOf(0.0) }

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
                text = "Seleccionar Servicios",
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
                    text = "Elige los servicios que deseas para tu vehículo.",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            servicios.forEach { (nombre, precio) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Checkbox(
                        checked = selectedServices.contains(nombre to precio),
                        onCheckedChange = {
                            if (it) {
                                selectedServices.add(nombre to precio)
                                totalPrice += precio
                            } else {
                                selectedServices.remove(nombre to precio)
                                totalPrice -= precio
                            }
                        }
                    )
                    Text(
                        text = "$nombre - \$${precio}",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Text(
                text = "Total: \$${totalPrice}",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(
                onClick = {
                    if (selectedServices.isNotEmpty()) {
                        val registro = registroLavadaRepository.insertar(
                            registro(
                                vehiculoId = vehiculoId,
                                servicioId = 0, // Aquí puede ser el ID del servicio real si está en la base de datos.
                                fechaLavado = "Fecha actual",
                                horaInicio = "Hora de inicio",
                                horaFin = "Hora de fin",
                                precioTotal = totalPrice
                            )
                        )
                                    navController.navigate ("history")
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Guardar y ver historial")
            }
        }
    }
}

