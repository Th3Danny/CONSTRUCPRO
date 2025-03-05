package com.example.myapplication.core.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Verificar si hay datos en la notificación
        remoteMessage.notification?.let {
            showNotification(it.title ?: "Notificación", it.body ?: "Mensaje recibido")
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "firebase_channel"
        val notificationId = System.currentTimeMillis().toInt()

        // Crear canal de notificación (para Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notificaciones Firebase",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para recibir notificaciones push de Firebase"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Construcción de la notificación
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        // Verificar permisos en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Aquí puedes solicitar el permiso en tu Activity principal si aún no se ha concedido
            return
        }

        // Mostrar la notificación
        notificationManager.notify(notificationId, notification)
    }


    private fun sendTokenToServer(token: String) {
        val url = "https://tu-backend.com/api/token"  // 🔹 Cambia esto por tu API
        val json = JSONObject().apply {
            put("token", token)
        }
        val body = RequestBody.create(MediaType.parse("application/json"), json.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("FCM", "Error enviando token al backend: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("FCM", "Token enviado con éxito al backend")
            }
        })
    }
}
