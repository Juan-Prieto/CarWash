package com.example.carwash.ui.theme.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carwash.Models.RegistroLavado
import com.example.carwash.Repository.RegistroLavadoRepository
import kotlinx.coroutines.launch


@Composable
fun RegistroListScreen(
    registroLavadoRepository: RegistroLavadoRepository,
    navController: NavController
) {
    // Usamos un coroutineScope para obtener los datos en segundo plano
    val scope = rememberCoroutineScope()

    // Estado para almacenar la lista de registros
    var registros by remember { mutableStateOf<List<RegistroLavado>>(emptyList()) }

    // Cargar los registros cuando la pantalla se carga
    LaunchedEffect(Unit) {
        scope.launch {
            val registrosFromDB = registroLavadoRepository.obtener()
            registros = registrosFromDB
        }
    }

    // Mostrar los registros centrados en una LazyColumn
    if (registros.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente
            verticalArrangement = Arrangement.Center // Centra los elementos verticalmente
        ) {
            items(registros) { registro ->
                RegistroItem(registro)
            }
        }
    } else {
        // Muestra un texto centrado si no hay registros
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "No hay registros disponibles")
        }
    }

    Button(
        onClick = {
            navController.navigate("Home")
        },
        modifier = Modifier.padding(8.dp)
    ) {
        Text("Volver")
    }
}

@Composable
fun RegistroItem(registro: RegistroLavado) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f) // Hacemos que los ítems tengan el 80% del ancho de la pantalla
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Centra el contenido de cada ítem
        ) {
            Text(text = "Fecha de Lavado: ${registro.fechaLavado}")
            Text(text = "Hora de Inicio: ${registro.horaInicio}")
            Text(text = "Hora de Fin: ${registro.horaFin}")
            Text(text = "Precio Total: \$${registro.precioTotal}")
        }
    }
}