package com.example.carwash.ui.theme.Screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.Servicio
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import kotlinx.coroutines.launch
import java.util.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun ServiceSelectionScreen(
    navController: NavController,
    vehiculoId: Int,
    servicioRepository: ServicioRepository,
    registroLavadoRepository: RegistroLavadoRepository
) {
    val context = LocalContext.current
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
    var totalPrice by remember { mutableStateOf(0.0) }
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Función para abrir el selector de fecha
    val showDatePicker = {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fechaLavado = "$year-${month + 1}-$dayOfMonth"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Función para abrir el selector de hora
    val showTimePicker = { onTimeSelected: (String) -> Unit ->
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                onTimeSelected("${hourOfDay.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}")
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Seleccionar Servicio", fontSize = 24.sp, modifier = Modifier.padding(16.dp))

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
                            Text(text = "${servicio.first} - \$${servicio.second}")
                        },
                        onClick = {
                            selectedService = servicio
                            totalPrice = servicio.second
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = fechaLavado,
            onValueChange = { },
            label = { Text("Fecha Lavado") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(32.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = showDatePicker) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar Fecha")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = horaInicio,
            onValueChange = { },
            label = { Text("Hora Inicio") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(32.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showTimePicker { horaInicio = it } }) {
                    Icon(Icons.Default.AccessTime, contentDescription = "Seleccionar Hora Inicio")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = horaFin,
            onValueChange = { },
            label = { Text("Hora Fin") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(32.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showTimePicker { horaFin = it } }) {
                    Icon(Icons.Default.AccessTime, contentDescription = "Seleccionar Hora Fin")
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        Text(
            text = "Precio Total: \$${totalPrice}",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (mensajeError.isNotEmpty()) {
            Text(text = mensajeError, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                if (selectedService != null && fechaLavado.isNotEmpty() && horaInicio.isNotEmpty() && horaFin.isNotEmpty()) {
                    scope.launch {
                        registroLavadoRepository.insertar(
                            RegistroLavado(
                                vehiculoId = vehiculoId,
                                servicioId = 1,
                                fechaLavado = fechaLavado,
                                horaInicio = horaInicio,
                                horaFin = horaFin,
                                precioTotal = totalPrice
                            )
                        )
                        servicioRepository.insertar(
                            Servicio(
                                nombre = selectedService?.first ?: "Desconocido",
                                precio = totalPrice
                            )
                        )
                        navController.navigate("Home")
                    }
                } else {
                    mensajeError = "Por favor, complete todos los campos."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
        ) {
            Text("Confirmar Servicio", fontSize = 15.sp)
        }
    }
}