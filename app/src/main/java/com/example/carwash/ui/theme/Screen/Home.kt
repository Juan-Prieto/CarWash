
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.Vehiculo
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController, clienteId: Int, vehiculoRepository: VehiculoRepository, nombre: String, apellido: String) {
    var vehicles by remember { mutableStateOf<List<Vehiculo>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Cargar los vehículos del cliente específico cuando se monta la pantalla
    LaunchedEffect(clienteId) {
        coroutineScope.launch {
            vehicles = withContext(Dispatchers.IO) {
                vehiculoRepository.obtenerVehiculosPorCliente(clienteId)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Mensaje de Bienvenida y Botón de Sign Out
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mensaje de bienvenida usando el nombre y apellido del cliente
                Text(
                    text = "Bienvenido, $nombre $apellido",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                // Botón "Sign Out" sin relleno, estilo similar al botón de "Sign In"
                TextButton(onClick = {
                    navController.navigate("Login")
                }) {
                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign Out")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sign Out")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de Add Vehicle, List Services y Add Service
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    navController.navigate("AddVehicle/$clienteId")
                }) {
                    Text(text = "Add Vehicle")
                }

                Button(onClick = {
                    navController.navigate("ListServices/$clienteId")
                }) {
                    Text(text = "List Services")
                }

                Button(onClick = {
                    if (vehicles.isEmpty()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Registre primero su vehículo para utilizar nuestros servicios")
                        }
                    } else {
                        // Navegar a la pantalla de servicios si tiene vehículos
                        navController.navigate("ServiceScreen/$clienteId")
                    }
                }) {
                    Text(text = "Add Service")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CalendarHeader()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CalendarHeader() {
    // Obtener la fecha actual
    val currentDate = LocalDate.now()
    val currentDay = currentDate.dayOfMonth
    val currentDayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val currentMonth = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val currentYear = currentDate.year

    // Header de calendario (día actual y "Today" si corresponde)
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Día de la semana y fecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "$currentDay",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = currentDayOfWeek,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Light
                )

                Text(
                    text = "$currentMonth $currentYear",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Parte derecha: Texto "Today"
            Text(
                text = "Today",
                color = Color(0xFF1A73E8),
                fontWeight = FontWeight.Bold
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Días del mes (visualización de 7 días, incluyendo el actual)
    val startDay = currentDay - 3
    val endDay = currentDay + 3

    CustomDivider()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        (startDay..endDay).forEach { day ->
            val isToday = day == currentDay

            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isToday) {
                    Box(
                        modifier = Modifier
                            .size(height = 35.dp, width = 29.dp)
                            .background(Color(0xFF1A73E8), shape = MaterialTheme.shapes.extraSmall),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$day",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Text(text = "$day", color = Color.Gray)
                }
            }
        }
    }
    CustomDivider()
}

@Composable
fun CustomDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 0.2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp)
    )
}
