import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.LocalContext
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Models.Servicio
import com.example.carwash.Models.Vehiculo
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.ServicioRepository
import com.example.carwash.Repository.VehiculoRepository

@Composable
fun ServiceScreen(
    clienteId: Int,
    vehiculoRepository: VehiculoRepository,
    servicioRepository: ServicioRepository,
    registroLavadoRepository: RegistroLavadoRepository,
    navController: NavController
) {
    var vehicles by remember { mutableStateOf<List<Vehiculo>>(emptyList()) }
    val services = listOf(
        Servicio(servicioID = 1, nombre = "Desinfección con Ozono", precio = 45000.0),
        Servicio(servicioID = 2, nombre = "Lavado General con Chasis", precio = 49000.0),
        Servicio(servicioID = 3, nombre = "Combo motor y desengrasante", precio = 55000.0),
        Servicio(servicioID = 4, nombre = "Combo Protección", precio = 88000.0),
        Servicio(servicioID = 5, nombre = "Combo Reestreno", precio = 143000.0)
    )
    var selectedVehicleId by remember { mutableStateOf<Int?>(null) }
    var selectedService by remember { mutableStateOf<Servicio?>(null) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTimeSlot by remember { mutableStateOf<Pair<String, String>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Cargar los vehículos del cliente específico cuando se monta la pantalla
    LaunchedEffect(clienteId) {
        coroutineScope.launch {
            vehicles = withContext(Dispatchers.IO) {
                vehiculoRepository.obtenerVehiculosPorCliente(clienteId)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icono de flecha hacia atrás alineado a la izquierda
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
        }

        // Centrar "Select vehicle"
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Select vehicle",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }

        // LazyRow para mostrar los vehículos
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(vehicles) { vehicle ->
                    VehicleCard(
                        name = "${vehicle.marca} ${vehicle.tipo}",
                        details = "${getColorName(vehicle.color)} - ${vehicle.placa}",
                         selected = selectedVehicleId == vehicle.vehiculoID,
                        onClick = {
                            selectedVehicleId = vehicle.vehiculoID
                        }
                    )
                }
            }
        }

        // Línea de separación
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Centrar "Select package"
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    "Select package",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }

        // LazyRow para mostrar los paquetes de servicios
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(services) { service ->
                    ServicePackageCard(
                        service = service,
                        selected = selectedService == service,
                        onClick = {
                            selectedService = service
                        }
                    )
                }
            }
        }

        // Línea de separación
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Sección de selección de fecha y hora
        item {
            DateAndTimeSelection(
                selectedDate = selectedDate,
                onDateSelected = { date -> selectedDate = date },
                selectedTimeSlot = selectedTimeSlot,
                onTimeSlotSelected = { slot -> selectedTimeSlot = slot }
            )
        }

        // Subtotal y botón "Guardar"
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Subtotal \$${selectedService?.precio?.toInt() ?: 0}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Button(
                    onClick = {
                        if (selectedVehicleId != null && selectedService != null && selectedDate != null && selectedTimeSlot != null) {
                            coroutineScope.launch {
                                // Inserta el registro de lavado con el servicio seleccionado
                                val registro = RegistroLavado(
                                    vehiculoId = selectedVehicleId!!,
                                    servicioId = selectedService!!.servicioID, // Asegúrate de que se esté asignando el ID del servicio seleccionado
                                    fechaLavado = selectedDate.toString(),
                                    horaInicio = selectedTimeSlot!!.first,
                                    horaFin = selectedTimeSlot!!.second,
                                    precioTotal = selectedService!!.precio
                                )
                                registroLavadoRepository.insertar(registro)

                                // Mostrar mensaje de registro exitoso y navegar a la pantalla de registros
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    navController.navigate("RegistroListScreen/$clienteId")
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Guardar", color = Color.White)
                }
            }
        }
    }
}

// Función de selección de fecha y hora
@Composable
fun DateAndTimeSelection(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    selectedTimeSlot: Pair<String, String>?,
    onTimeSlotSelected: (Pair<String, String>) -> Unit
) {
    // Generar la lista de fechas (7 días a partir de hoy)
    val today = LocalDate.now()
    val dates = (0..6).map { today.plusDays(it.toLong()) }

    // Generar los intervalos de tiempo
    val startTime = LocalTime.of(7, 0)
    val endTime = LocalTime.of(19, 0)
    val timeSlots = generateTimeIntervals(startTime, endTime, 60)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        // Selección de fecha
        Text(
            "Select day",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(dates) { date ->
                val isSelected = date == selectedDate
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            if (isSelected) Color(0xFF1A73E8) else Color.LightGray,
                            shape = MaterialTheme.shapes.small
                        )
                        .clickable { onDateSelected(date) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Mostrar el nombre corto del día usando Compose y Kotlin
                        Text(
                            text = date.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() },
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 12.sp
                        )
                        Text(
                            text = date.dayOfMonth.toString(),
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Espaciado entre la selección de día y tiempo
        Spacer(modifier = Modifier.height(16.dp))

        // Selección de tiempo
        Text(
            "Select time",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            timeSlots.chunked(3).forEach { rowSlots ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    rowSlots.forEach { slot ->
                        val isSelected = slot == selectedTimeSlot
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                                .background(
                                    if (isSelected) Color(0xFF1A73E8) else Color.LightGray,
                                    shape = MaterialTheme.shapes.small
                                )
                                .clickable { onTimeSlotSelected(slot) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${slot.first} - ${slot.second}",
                                color = if (isSelected) Color.White else Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// Función para generar intervalos de tiempo
fun generateTimeIntervals(start: LocalTime, end: LocalTime, intervalMinutes: Int = 60): List<Pair<String, String>> {
    val intervals = mutableListOf<Pair<String, String>>()
    var currentTime = start
    while (currentTime.plusMinutes(intervalMinutes.toLong()) <= end) {
        val nextTime = currentTime.plusMinutes(intervalMinutes.toLong())
        intervals.add(
            Pair(
                currentTime.format(DateTimeFormatter.ofPattern("h:mm a")),
                nextTime.format(DateTimeFormatter.ofPattern("h:mm a"))
            )
        )
        currentTime = nextTime
    }
    return intervals
}

// Composable para mostrar un paquete de servicio
@Composable
fun ServicePackageCard(
    service: Servicio,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF1A73E8) else Color.LightGray
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Nombre del servicio
            Text(
                text = service.nombre,
                color = if (selected) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // Precio del servicio
            Text(
                text = "${service.precio} COP",
                color = if (selected) Color.White else Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

// Composable para mostrar un vehículo
@Composable
fun VehicleCard(
    name: String,
    details: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFF1A73E8) else Color.LightGray
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Nombre del vehículo
            Text(
                text = name,
                color = if (selected) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            // Detalles del vehículo
            Text(
                text = details,
                color = if (selected) Color.White else Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

// Función para obtener el nombre del color
fun getColorName(colorCode: String): String {
    return when (colorCode) {
        "#D3D3D3" -> "Light Gray"
        "#808080" -> "Gray"
        "#A9A9A9" -> "Dark Gray"
        "#000000" -> "Black"
        "#FF0000" -> "Red"
        "#0000FF" -> "Blue"
        "#FFFFFF" -> "White"
        "#008000" -> "Green"
        "#FFFF00" -> "Yellow"
        "#FFA500" -> "Orange"
        "#800080" -> "Purple"
        "#00FFFF" -> "Cyan"
        else -> colorCode // Muestra el código de color si no se reconoce
    }
}
