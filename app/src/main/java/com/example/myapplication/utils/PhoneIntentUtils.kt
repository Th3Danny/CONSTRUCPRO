package com.example.myapplication.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openPhoneChooser(context: Context, phoneNumber: String) {
    val callIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }

    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://wa.me/$phoneNumber")
        `package` = "com.whatsapp"
    }

    // Verifica si WhatsApp está instalado
    val packageManager = context.packageManager
    val whatsappResolved = whatsappIntent.resolveActivity(packageManager) != null

    val extraIntents = if (whatsappResolved) {
        arrayOf(whatsappIntent)
    } else {
        Toast.makeText(context, "WhatsApp no está instalado", Toast.LENGTH_SHORT).show()
        emptyArray()
    }

    val chooser = Intent.createChooser(callIntent, "Selecciona una opción")
    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents)

    context.startActivity(chooser)
}
