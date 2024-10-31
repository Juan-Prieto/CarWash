package com.example.carwash.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carwash.Models.RegistroLavadoDetalles
import com.example.carwash.R
import com.example.carwash.Repository.RegistroLavadoRepository
import com.example.carwash.utils.generarFacturaPDF
import kotlinx.coroutines.launch

@Composable
fun RegistroListScreen(
    registroLavadoRepository: RegistroLavadoRepository,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    var registros by remember { mutableStateOf<List<RegistroLavadoDetalles>>(emptyList()) }
    var selectedRegistro by remember { mutableStateOf<RegistroLavadoDetalles?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            val registrosFromDB = registroLavadoRepository.getAllRegistrosConDetalles()
            registros = registrosFromDB
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Historial de Servicios",
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        if (registros.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(registros) { registro ->
                    RegistroItem(registro) { selectedRegistro = it }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "No hay registros disponibles", color = Color.Gray)
            }
        }

        Button(
            onClick = {
                navController.navigate("Home")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
        ) {
            Text("Volver", fontSize = 15.sp)
        }
    }

    selectedRegistro?.let { registro ->
        FacturaDialog(
            registro = registro,
            onDismiss = { selectedRegistro = null }
        )
    }
}

@Composable
fun RegistroItem(
    registro: RegistroLavadoDetalles,
    onShowDetails: (RegistroLavadoDetalles) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onShowDetails(registro) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Fecha de Lavado: ${registro.registroLavado.fechaLavado}", fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Hora de Inicio: ${registro.registroLavado.horaInicio}", fontSize = 14.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Precio Total: \$${registro.registroLavado.precioTotal}", fontSize = 16.sp, color = Color.Black)
        }
    }
}

@Composable
fun FacturaDialog(
    registro: RegistroLavadoDetalles,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo de la Empresa",
                    modifier = Modifier.size(64.dp)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Contenido de la factura
                Text(text = "Datos de la Empresa", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "CarWash Inc.")
                Text(text = "Dirección: Calle Ficticia 123")
                Text(text = "Teléfono: +1 234 567 890")
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Información del Cliente", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Nombre: ${registro.cliente.nombre} ${registro.cliente.apellido}")
                Text(text = "Teléfono: ${registro.cliente.telefono}")
                Text(text = "Correo: ${registro.cliente.email}")
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Detalle del Servicio", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Tipo de Servicio: ${registro.servicio.nombre}")
                Text(text = "Hora de Inicio: ${registro.registroLavado.horaInicio}")
                Text(text = "Hora de Fin: ${registro.registroLavado.horaFin}")
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Valor Total: \$${registro.registroLavado.precioTotal}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        val file = generarFacturaPDF(context, registro)
                        if (file != null) {
                            Toast.makeText(context, "PDF guardado en: ${file.absolutePath}", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Error al guardar el PDF", Toast.LENGTH_LONG).show()
                        }
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Text("Descargar PDF")
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    Text("Cerrar")
                }
            }
        }
    )
}
