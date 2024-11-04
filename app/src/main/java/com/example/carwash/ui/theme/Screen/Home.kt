import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carwash.Models.Vehiculo
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        CalendarHeader()

        Spacer(modifier = Modifier.height(16.dp))


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader() {
    // Obtener la fecha actual
    val currentDate = LocalDate.now()
    val currentDay = currentDate.dayOfMonth
    val currentDayOfWeek =
        currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val currentMonth = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val currentYear = currentDate.year

    // Header de calendario (día actual y "Today" si corresponde)
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Día de la semana y fecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Text(
                text = "$currentDay",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "$currentDayOfWeek",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Light
                )

                Text(
                    text = "$currentMonth $currentYear",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Parte derecha: Texto "Today"
            Text(
                text = "Today",
                color = Color(0xFF1A73E8),
                fontWeight = FontWeight.Bold,

            )
        }

    }

    Spacer(modifier = Modifier.height(8.dp))

    // Días del mes (visualización de 7 días, incluyendo el actual)
    val startDay = currentDay - 3
    val endDay = currentDay + 3

    CustomDivider()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        (startDay..endDay).forEach { day ->
            val isToday = day == currentDay

            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isToday) {
                    Box(
                        modifier = Modifier
                            .size(height = 35.dp, width = 29.dp)
                            .background(Color(0xFF1A73E8), shape = MaterialTheme.shapes.extraSmall),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$day",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Text(text = "$day", color = Color.Gray)
                }
            }
        }
    }
    CustomDivider()
    TimelineItem("10:00", "11:00", Vehiculo(1, 1, "Mazda", "2021", "pdf43r", "rojo", "SEDAN" ))
}

@Composable
fun TimelineItem(startTime: String, endTime: String, vehiculo: Vehiculo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Parte izquierda: Hora de inicio y finalización del evento
        Column(
            modifier = Modifier.weight(0.3f), // Ajusta el ancho de la columna izquierda
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = startTime,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = endTime,
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Parte derecha: Tarjeta con detalles del vehículo
        Card(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A73E8) // Color gris claro
            ),
            shape = RoundedCornerShape(8.dp)

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Shine", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DirectionsCar, contentDescription = "Vehículo", tint = Color.White, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${vehiculo.marca}, ${vehiculo.modelo}", color = Color.White, fontWeight = FontWeight.Normal)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = "Placa", tint = Color.White, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Placa: ${vehiculo.placa}", color = Color.White)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Palette, contentDescription = "Color", tint = Color.White, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Color: ${vehiculo.color}", color = Color.White)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.DirectionsCar, contentDescription = "Tipo", tint = Color.White, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Tipo: ${vehiculo.tipo}", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CustomDivider() {
    Divider(
        color = Color.LightGray, // Color de la línea divisoria
        thickness = 0.2.dp, // Grosor de la línea
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp)// Se extiende a lo ancho del contenedor
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun ver() {
    CalendarScreen()
}