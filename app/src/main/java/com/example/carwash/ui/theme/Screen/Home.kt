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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.carwash.Models.RegistroLavadoDetalles
import com.example.carwash.Models.Vehiculo
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navController: NavController,
    clienteId: Int,
    vehiculoRepository: VehiculoRepository,
    registroLavadoRepository: RegistroLavadoRepository,
    nombre: String,
    apellido: String
) {
    var vehicles by remember { mutableStateOf<List<Vehiculo>>(emptyList()) }
    var registros by remember { mutableStateOf<List<RegistroLavadoDetalles>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Cargar los vehículos y registros del cliente específico cuando se monta la pantalla
    LaunchedEffect(clienteId) {
        coroutineScope.launch {
            vehicles = withContext(Dispatchers.IO) {
                vehiculoRepository.obtenerVehiculosPorCliente(clienteId)
            }
            registros = withContext(Dispatchers.IO) {
                registroLavadoRepository.getAllRegistrosConDetalles()
                    .filter { it.cliente?.clienteID == clienteId }
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
                    navController.navigate("RegistroListScreen/$clienteId")
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

            // Mostrar registros de servicios
            Text(
                text = "Historial de Servicios",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(registros) { registro ->
                    RegistroCard(registro)
                }
            }
        }
    }
}

@Composable
fun RegistroCard(registro: RegistroLavadoDetalles) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A73E8)),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Tipo de servicio
            Text(
                text = registro.servicio?.nombre ?: "Servicio Desconocido",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Información del vehículo
            Text(
                text = "${registro.vehiculo?.tipo ?: "Tipo Desconocido"} - ${registro.vehiculo?.marca ?: "Marca Desconocida"}",
                color = Color.White,
                fontSize = 14.sp
            )

            // Modelo del vehículo
            Text(
                text = "Modelo: ${registro.vehiculo?.modelo ?: "Desconocido"}",
                color = Color.White,
                fontSize = 14.sp
            )

            // Intervalo de tiempo del servicio
            Text(
                text = "Duración: ${registro.registroLavado.horaInicio} - ${registro.registroLavado.horaFin}",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}
