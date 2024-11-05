package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.navigation.NavController
import com.example.carwash.R
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.ImeAction
import com.example.carwash.Models.Vehiculo
import com.example.carwash.Repository.VehiculoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(clienteId: Int, navController: NavController, vehiculoRepository: VehiculoRepository) {

    var vehicleType by remember { mutableStateOf("SUV") }
    var otherVehicleType by remember { mutableStateOf("") }
    val vehicleTypes = remember { mutableStateListOf("SUV", "Sedan", "Minivan", "Roadster").apply { add("Other") } }
    val scope = rememberCoroutineScope()

    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var colorVehicle by remember { mutableStateOf(Color.Transparent) }

    var showBottomSheet by remember { mutableStateOf(false) }
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//    val coroutineScope = rememberCoroutineScope()

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

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start
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
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )

                Button(
                    onClick = {
                        if (otherVehicleType.isNotEmpty() && !vehicleTypes.contains(otherVehicleType)) {
                            vehicleTypes.add(vehicleTypes.size - 1, otherVehicleType)
                            vehicleType = otherVehicleType
                        }
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    enabled = otherVehicleType.isNotEmpty()
                ) {
                    Text("Add Vehicle Type")
                }
            }

            // Campos de entrada de texto
            OutlinedTextField(
                value = brand,
                onValueChange = {
                    brand = it
                },
                label = { Text("Brand") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                isError = brand.isEmpty()
            )

            OutlinedTextField(
                value = model,
                onValueChange = {
                    model = it
                },
                label = { Text("Model") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                isError = model.isEmpty()
            )

            OutlinedTextField(
                value = plateNumber,
                onValueChange = {
                    plateNumber = it
                },
                label = { Text("Plate Number (Optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
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
                listOf(
                    Color.LightGray,
                    Color.Gray,
                    Color.DarkGray,
                    Color.Black,
                    Color.Red,
                    Color.Blue
                ).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(color, CircleShape)
                            .clickable {
                                colorVehicle = color
                            }
                    )
                }
            }

            // Botón Guardar
            Button(
                onClick = {
                    // Insertar el vehículo en la base de datos
                    if (brand.isNotEmpty() && model.isNotEmpty()) {
                        scope.launch {
                            val vehiculo = Vehiculo(
                                clienteId = clienteId,
                                marca = brand,
                                modelo = model,
                                placa = plateNumber,
                                color = colorToHex(colorVehicle),
                                tipo = vehicleType
                            )

                            vehiculoRepository.insertar(vehiculo)

                            // Mostrar mensaje de éxito
                            withContext(Dispatchers.Main) {
                                showBottomSheet = true
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = brand.isNotEmpty() && model.isNotEmpty()
            ) {
                Text(
                    text = "Save Vehicle",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            if (showBottomSheet) {
                ShowSaveDialog(navController = navController, clienteId = clienteId)
            }
        }
    }
}

@Composable
fun VehicleTypeOption(type: String, isSelected: Boolean = false, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSaveDialog(navController: NavController, clienteId: Int) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 17.dp)
                .height(250.dp), // Incrementa el tamaño para acomodar el botón
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0xFF1A73E8), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Saved Icon",
                        tint = Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Vehicle Saved!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón "Add Service"
                Button(
                    onClick = {
                        navController.navigate("ServiceScreen/$clienteId") // Navegar a la pantalla de servicios
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Add Service",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun colorToHex(color: Color): String {
    return "#" + Integer.toHexString(color.toArgb()).substring(2).uppercase()
}

