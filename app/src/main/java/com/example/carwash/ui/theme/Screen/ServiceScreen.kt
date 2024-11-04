package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carwash.DAO.VehiculoDAO
import com.example.carwash.Models.Vehiculo
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarWashRequestScreen(vehiculoRepository: VehiculoRepository) {
    var vehicles by remember { mutableStateOf<List<Vehiculo>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            vehicles = withContext(Dispatchers.IO) { vehiculoRepository.obtener() }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        // Section: Select vehicle
        item {
            Text("Select vehicle", style = MaterialTheme.typography.bodyMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(vehicles) { vehicle ->
                    VehicleCard(
                        name = "${vehicle.marca} ${vehicle.tipo}",
                        details = "${vehicle.color} - ${vehicle.placa}",
                        selected = false // Adjust selection logic as needed
                    )
                }
            }
        }
    }
}

@Composable
fun VehicleCard(name: String, details: String, selected: Boolean = false) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp),
        colors = if (selected) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary) else CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(name, fontSize = 12.sp)
            Text(details, fontSize = 10.sp)
        }
    }
}


