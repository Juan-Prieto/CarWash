package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.DAO.VehiculoDAO
import com.example.carwash.Models.Vehiculo
import com.example.carwash.R
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ServiceScreen(
    clienteId: Int,
    vehiculoRepository: VehiculoRepository,
    navController: NavController
) {
    var vehicles by remember { mutableStateOf<List<Vehiculo>>(emptyList()) }
    var selectedVehicleId by remember { mutableStateOf<Int?>(null) } // Estado para almacenar el vehículo seleccionado
    val coroutineScope = rememberCoroutineScope()

    // Cargar los vehículos del cliente específico cuando se monta la pantalla
    LaunchedEffect(clienteId) {
        coroutineScope.launch {
            vehicles = withContext(Dispatchers.IO) {
                vehiculoRepository.obtenerVehiculosPorCliente(clienteId)
            }
        }
    }

    IconButton(
        onClick = {
            navController.navigate("HomeScreen/$clienteId")
        },
        modifier = Modifier
            .padding(top = 20.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Mostrar los vehículos asociados al cliente
        item {
            Text("Select vehicle", style = MaterialTheme.typography.bodyMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(vehicles) { vehicle ->
                    VehicleCard(
                        name = "${vehicle.marca} ${vehicle.tipo}",
                        details = "${vehicle.color} - ${vehicle.placa}",
                        selected = vehicle.vehiculoID == selectedVehicleId, // Ajusta el color de la tarjeta según si está seleccionada
                        onClick = {
                            selectedVehicleId = vehicle.vehiculoID // Actualiza el vehículo seleccionado
                        }
                    )
                }
            }
        }
    }
}

// Vehículo Card modificado para parecerse al diseño de AddVehicle
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
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono del vehículo
            Image(
                painter = painterResource(id = R.drawable.mazda),
                contentDescription = "Vehículo",
                modifier = Modifier.size(80.dp)
            )

            // Marca y tipo del vehículo
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