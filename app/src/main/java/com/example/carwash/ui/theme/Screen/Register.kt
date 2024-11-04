package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.Cliente
import com.example.carwash.Repository.ClienteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm(navController: NavController, clienteRepository: ClienteRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var mostrarContraseña by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Validadores para cada campo
    val validarTelefono: (String) -> Boolean = { it.matches(Regex("^[0-9+\\-()\\s]*$")) }
    val validarNombreApellido: (String) -> Boolean = { it.matches(Regex("^[a-zA-Z\\s]*$")) }
    val validarEmail: (String) -> Boolean =
        { it.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Card(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                ) {
                Text(
                    text = "Fill out this form",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Please complete your information.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 24.dp),
                    fontSize = 15.sp,
                    color = Color.Gray
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        if (validarNombreApellido(it)) nombre = it
                    },
                    label = {Text(text = "First Name",color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = apellido,
                    onValueChange = {
                        if (validarNombreApellido(it)) apellido = it
                    },
                    label = { Text(text = "Last Name", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it // Permitir cualquier entrada temporalmente
                    },
                    label = { Text(text = "Email", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),

                )

                OutlinedTextField(
                    value = telefono,
                    onValueChange = {
                        if (validarTelefono(it)) telefono = it
                    },
                    label = { Text("Phone Number", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                )

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    label = { Text("Password", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (mostrarContraseña) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { mostrarContraseña = !mostrarContraseña }) {
                            Icon(
                                imageVector = if (mostrarContraseña) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (mostrarContraseña) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    )
                )

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("By creating an account, you agree to our ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Terms")
                            }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                }

                Button(
                    onClick = {
                        if (nombre.isNotBlank() && apellido.isNotBlank() && telefono.isNotBlank() &&
                            email.isNotBlank() && contraseña.isNotBlank() && contraseña.length >= 8
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
                                        navController.navigate("Login")
                                    }
                                }
                            } else {
                                mensajeError = "El correo electrónico no es válido."
                            }
                        } else {
                            mensajeError =
                                "Por favor, completa todos los campos y asegúrate de que la contraseña tenga al menos 8 caracteres."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Continue")
                }
            }
        }
    }
}
