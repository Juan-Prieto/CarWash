package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.Servicio
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import kotlinx.coroutines.launch

@Composable
fun ServiceSelectionScreen(
    navController: NavController,
    vehiculoId: Int,
    servicioRepository: ServicioRepository,
    registroLavadoRepository: RegistroLavadoRepository
) {
    val servicios = listOf(
        "Lavado Exterior" to 10000.0,
        "Lavado Interior" to 15000.0,
        "Lavado Full" to 35000.0,
        "Lavado Premium" to 60000.0
    )

    var selectedService by remember { mutableStateOf<Pair<String, Double>?>(null) }
    var fechaLavado by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var totalPrice by remember { mutableStateOf(0.0) } // Precio total actualizado
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Seleccionar Servicio", fontSize = 24.sp)

        // Dropdown para seleccionar el servicio
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedService?.first ?: "Selecciona un servicio")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                servicios.forEach { servicio ->
                    DropdownMenuItem(
                        text = {
                            Text(text = "${servicio.first} - \$${servicio.second}") // Texto del ítem
                        },
                        onClick = {
                            selectedService = servicio // Actualizar el servicio seleccionado
                            totalPrice = servicio.second // Actualizar el precio total
                            expanded = false // Cerrar el menú desplegable
                        }
                    )
                }
            }
        }

        // Campo para ingresar la fecha del lavado
        TextField(
            value = fechaLavado,
            onValueChange = { fechaLavado = it },
            label = { Text("Fecha Lavado (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Campo para ingresar la hora de inicio
        TextField(
            value = horaInicio,
            onValueChange = { horaInicio = it },
            label = { Text("Hora Inicio (HH:MM)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Campo para ingresar la hora de fin
        TextField(
            value = horaFin,
            onValueChange = { horaFin = it },
            label = { Text("Hora Fin (HH:MM)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Mostrar el precio total
        Text(
            text = "Precio Total: \$${totalPrice}",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Botón para registrar el servicio
        Button(onClick = {
            if (selectedService != null && fechaLavado.isNotEmpty() && horaInicio.isNotEmpty() && horaFin.isNotEmpty()) {
                // Inserción del registro de lavado
                scope.launch {
                    registroLavadoRepository.insertar(
                        RegistroLavado(
                            vehiculoId = vehiculoId,
                            servicioId = 1, // Aquí debes gestionar el ID real del servicio seleccionado
                            fechaLavado = fechaLavado,
                            horaInicio = horaInicio,
                            horaFin = horaFin,
                            precioTotal = totalPrice
                        )
                    )

                    // Registrar el servicio elegido (nombre y precio)
                    servicioRepository.insertar(
                        Servicio(
                            nombre = selectedService?.first ?: "Desconocido",
                            precio = totalPrice
                        )
                    )

                    navController.navigate("Revisar pendientes") // Navegar al historial o a la siguiente pantalla
                }
            } else {
                mensajeError = "Por favor, completa todos los campos."
            }
        }) {
            Text("Confirmar Servicio")
        }

        // Mostrar mensaje de error
        if (mensajeError.isNotEmpty()) {
            Text(text = mensajeError, color = Color.Red, modifier = Modifier.padding(8.dp))
        }
    }
}