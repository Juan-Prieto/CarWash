package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.Cliente
import com.example.carwash.R
import com.example.carwash.Repository.ClienteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(navController: NavController, clienteRepository: ClienteRepository) {
    // Estado para almacenar los datos del cliente
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    // Contenedor principal
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Imagen de fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Contenido sobre la imagen de fondo
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                // Espacio para el logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logotipo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                        .clip(RoundedCornerShape(5.dp))
                )

                // Título
                Text(
                    text = "Registrar Usuario",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                // Card con la descripción
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
                        text = "Registra tus datos y pulsa en el botón registrarse para disfrutar de nuestro servicio.",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Campos de entrada
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Registrar Usuario", fontSize = 24.sp)

                    // Campos de texto para los datos
                    TextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    TextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        label = { Text("Apellido") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    TextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    TextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    TextField(
                        value = contraseña,
                        onValueChange = { contraseña = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }

                // Mensajes de error o éxito
                if (mensajeError.isNotEmpty()) {
                    Text(
                        text = mensajeError,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                if (mensajeExito.isNotEmpty()) {
                    Text(
                        text = mensajeExito,
                        color = Color.Green,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Botones de acción
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // boton para registrar e ir a la pantalla de registrar vehiculo
                    Button(
                        onClick = {
                            if (nombre.isNotBlank() && apellido.isNotBlank() && telefono.isNotBlank() &&
                                email.isNotBlank() && direccion.isNotBlank() && contraseña.isNotBlank()
                            ) {
                                scope.launch {
                                    val clienteId = clienteRepository.insertar(
                                        Cliente(
                                            nombre = nombre,
                                            apellido = apellido,
                                            telefono = telefono,
                                            email = email,
                                            direccion = direccion,
                                            contraseña = contraseña
                                        )
                                    )
                                    withContext(Dispatchers.Main) {
                                        navController.navigate("registrovehiculo/$clienteId")
                                    }
                                }
                            } else {
                                mensajeError = "Faltan campos por llenar."
                            }
                        }
                    ) {
                        Text("Registrarse")
                    }

                    // Boton regresar a la pantalla de bienvenido
                    Button(
                        onClick = {
                            navController.navigate("welcome")
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
