package com.example.carwash.utils

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.widget.Toast
import com.example.carwash.Models.RegistroLavadoDetalles
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun generarFacturaPDF(context: Context, registro: RegistroLavadoDetalles): File? {
    // Crear un documento PDF
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Tamaño A4
    val page = pdfDocument.startPage(pageInfo)
    val canvas: Canvas = page.canvas

    // Configurar pintura para el texto
    val paint = Paint()
    paint.color = Color.BLACK
    paint.textSize = 16f

    // Escribir contenido en el PDF
    var yPosition = 50f
    canvas.drawText("Factura - CarWash Inc.", 200f, yPosition, paint)
    yPosition += 30f
    paint.textSize = 14f
    canvas.drawText("Dirección: Calle Ficticia 123", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Teléfono: +1 234 567 890", 50f, yPosition, paint)
    yPosition += 30f

    paint.textSize = 16f
    canvas.drawText("Datos del Cliente", 50f, yPosition, paint)
    yPosition += 20f
    paint.textSize = 14f
    canvas.drawText("Nombre: ${registro.cliente.nombre} ${registro.cliente.apellido}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Teléfono: ${registro.cliente.telefono}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Correo: ${registro.cliente.email}", 50f, yPosition, paint)
    yPosition += 30f

    paint.textSize = 16f
    canvas.drawText("Detalles del Servicio", 50f, yPosition, paint)
    yPosition += 20f
    paint.textSize = 14f
    canvas.drawText("Tipo de Servicio: ${registro.servicio.nombre}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Fecha: ${registro.registroLavado.fechaLavado}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Hora de Inicio: ${registro.registroLavado.horaInicio}", 50f, yPosition, paint)
    yPosition += 20f
    canvas.drawText("Hora de Fin: ${registro.registroLavado.horaFin}", 50f, yPosition, paint)
    yPosition += 30f

    paint.textSize = 16f
    canvas.drawText("Total a Pagar: \$${registro.registroLavado.precioTotal}", 50f, yPosition, paint)

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
