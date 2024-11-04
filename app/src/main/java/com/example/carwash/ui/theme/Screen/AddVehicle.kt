package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.carwash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(navController: NavController) {
    var vehicleType by remember { mutableStateOf("SUV") }
    var otherVehicleType by remember { mutableStateOf("") }
    val vehicleTypes = remember { mutableStateListOf("SUV", "Sedan").apply { add("Other") } }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Card(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Título
                Text(
                    text = "Add a vehicle",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Subtítulo
                Text(
                    text = "Select your type of vehicle",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Tipos de vehículos
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(vehicleTypes.size) { index ->
                        val type = vehicleTypes[index]
                        VehicleTypeOption(type = type, isSelected = (vehicleType == type)) {
                            vehicleType = type
                        }

                    }
                }

                // Campo de texto adicional si se selecciona "Other"
                if (vehicleType == "Other") {
                    OutlinedTextField(
                        value = otherVehicleType,
                        onValueChange = {
                            otherVehicleType = it
                        },
                        label = { Text("Specify Vehicle Type") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = {
                            if (otherVehicleType.isNotEmpty() && !vehicleTypes.contains(otherVehicleType)) {
                                vehicleTypes.add(vehicleTypes.size - 1, otherVehicleType)
                                vehicleType = otherVehicleType
                            }
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text("Add Vehicle Type")
                    }
                }

                // Campos de entrada de texto
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Brand") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Model") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Plate Number (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Selección de color
                Text(
                    text = "Select Color",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
                    horizontalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    listOf(Color.LightGray, Color.Gray, Color.DarkGray, Color.Black, Color.Red, Color.Blue).forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(color, CircleShape)
                                .clickable { }
                        )
                    }
                }

                // Botón Guardar
                Button(
                    onClick = { /* Acción para guardar el vehículo */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save Vehicle",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}
@Composable
fun VehicleTypeOption(type: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ){

        Box(
            modifier = Modifier
                .size(130.dp, 120.dp)
                .background(
                    if (isSelected) Color(0xFF1A73E8) else Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mazda), // Reemplaza con el ID de tu imagen
                    contentDescription = null,
                    modifier = Modifier
                        .size(86.dp)
                )
                Text(
                    text = type,
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}