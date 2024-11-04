package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.Cliente
import com.example.carwash.R
import com.example.carwash.Repository.ClienteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun RegisterScreen(navController: NavController, clienteRepository: ClienteRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Validadores para cada campo
    val validarTelefono: (String) -> Boolean = { it.matches(Regex("^[0-9+\\-()\\s]*$")) }
    val validarNombreApellido: (String) -> Boolean = { it.matches(Regex("^[a-zA-Z\\s]*$")) }
    val validarEmail: (String) -> Boolean = { it.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logotipo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Text(
                    text = "Registrar Usuario",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Thin,
                    color = Color(0xFF070707),
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            if (validarNombreApellido(it)) nombre = it
                        },
                        label = { Text("Nombre", fontSize = 14.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )

                    OutlinedTextField(
                        value = apellido,
                        onValueChange = {
                            if (validarNombreApellido(it)) apellido = it
                        },
                        label = { Text("Apellido", fontSize = 14.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                    )

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = {
                            if (validarTelefono(it)) telefono = it
                        },
                        label = { Text("Teléfono", fontSize = 14.sp) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it // Permitir cualquier entrada temporalmente
                        },
                        label = { Text("Correo Electrónico", fontSize = 14.sp) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(60.dp),
                        shape = RoundedCornerShape(32.dp)
                    )

                    OutlinedTextField(
                        value = contraseña,
                        onValueChange = { contraseña = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (nombre.isNotBlank() && apellido.isNotBlank() && telefono.isNotBlank() &&
                                email.isNotBlank() && contraseña.isNotBlank()
                            ) {
                                // Validación al momento de enviar el formulario
                                if (validarEmail(email)) {
                                    scope.launch {
                                        val clienteId = clienteRepository.insertar(
                                            Cliente(
                                                nombre = nombre,
                                                apellido = apellido,
                                                telefono = telefono,
                                                email = email,
                                                contraseña = contraseña
                                            )
                                        )
                                        withContext(Dispatchers.Main) {
                                            navController.navigate("registrovehiculo/$clienteId")
                                        }
                                    }
                                } else {
                                    mensajeError = "El correo electrónico no es válido."
                                }
                            } else {
                                mensajeError = "Faltan campos por llenar."
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp)

                    ) {
                        Text("Registrarse", fontSize = 15.sp)
                    }

                }
            }
        }
    }
}
