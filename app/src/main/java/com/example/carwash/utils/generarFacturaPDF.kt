package com.example.carwash.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.carwash.Models.RegistroLavadoDetalles
import com.example.carwash.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun generarFacturaPDF(context: Context, registro: RegistroLavadoDetalles): File? {
    // Crear un documento PDF
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Tamaño A4
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    // Configurar pintura para el texto y encabezados
    val paint = Paint()
    paint.color = Color.BLACK
    paint.textSize = 16f

    val titlePaint = Paint()
    titlePaint.color = Color.BLACK
    titlePaint.textSize = 24f
    titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

    val linePaint = Paint()
    linePaint.color = Color.GRAY
    linePaint.strokeWidth = 2f

    // Incluir logo
    val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
    val scaledLogo = Bitmap.createScaledBitmap(logoBitmap, 100, 100, false)
    canvas.drawBitmap(scaledLogo, (canvas.width - scaledLogo.width) / 2f, 20f, null)

    // Añadir título
    canvas.drawText("Factura - CarWash Inc.", 150f, 150f, titlePaint)

    // Línea divisoria
    canvas.drawLine(50f, 160f, canvas.width - 50f, 160f, linePaint)

    // Sección de datos de la empresa
    paint.textSize = 14f
    var yPosition = 180f
    canvas.drawText("CarWash Inc.", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Dirección: Calle 85b # 3 - 45A sur", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Teléfono: +57 234 567 890", 50f, yPosition, paint)
    yPosition += 30f

    // Sección de información del cliente
    titlePaint.textSize = 18f
    canvas.drawText("Datos del Cliente", 50f, yPosition, titlePaint)
    yPosition += 20f
    paint.textSize = 14f
    canvas.drawText("Nombre: ${registro.cliente?.nombre} ${registro.cliente?.apellido}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Teléfono: ${registro.cliente?.telefono}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Correo: ${registro.cliente?.email}", 50f, yPosition, paint)
    yPosition += 30f

    // Sección de detalles del servicio
    titlePaint.textSize = 18f
    canvas.drawText("Detalles del Servicio", 50f, yPosition, titlePaint)
    yPosition += 20f
    paint.textSize = 14f
    canvas.drawText("Tipo de Servicio: ${registro.servicio?.nombre}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Fecha: ${registro.registroLavado.fechaLavado}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Hora de Inicio: ${registro.registroLavado.horaInicio}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Hora de Fin: ${registro.registroLavado.horaFin}", 50f, yPosition, paint)
    yPosition += 30f

    // Sección de total
    titlePaint.textSize = 18f
    canvas.drawText("Total a Pagar", 50f, yPosition, titlePaint)
    yPosition += 20f
    paint.textSize = 16f
    canvas.drawText("\$${registro.registroLavado.precioTotal}", 50f, yPosition, paint)

    // Terminar página y cerrar documento
    pdfDocument.finishPage(page)

    // Guardar el archivo en la carpeta de Descargas
    val fileName = "factura_${registro.registroLavado.registroID}.pdf"
    val outputStream: OutputStream?

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Usar MediaStore para Android 10 y superior
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                outputStream = resolver.openOutputStream(uri)
                pdfDocument.writeTo(outputStream!!)
                outputStream.close()
                pdfDocument.close()
                Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_LONG).show()
                return File(uri.path)
            } else {
                throw Exception("No se pudo crear el archivo PDF")
            }
        } else {
            // Para versiones anteriores a Android 10
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)
            outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            outputStream.close()
            pdfDocument.close()
            Toast.makeText(context, "PDF guardado en: ${file.absolutePath}", Toast.LENGTH_LONG).show()
            return file
        }
    } catch (e: Exception) {
        e.printStackTrace()
        pdfDocument.close()
        Toast.makeText(context, "Error al guardar el PDF: ${e.message}", Toast.LENGTH_LONG).show()
        return null
    }
}
