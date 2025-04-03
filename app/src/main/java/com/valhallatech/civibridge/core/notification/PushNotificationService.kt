package com.valhallatech.civibridge.core.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.valhallatech.civibridge.MainActivity


@Suppress("MissingFirebaseInstanceTokenRefresh")
class PushNotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nuevo token de FCM: $token")
        saveFCMToken(token)
        subscribeToGlobalTopic()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val dataMap = remoteMessage.data

        val destination = dataMap["navigateTo"]
        val jobId = dataMap["jobId"]
        val companyPhone = dataMap["companyPhone"]
        val notificationType = dataMap["notificationType"]

        val jsonString = Gson().toJson(dataMap)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("navigateTo", destination)
            putString("jobIdFromNotification", jobId)
            putString("companyPhoneFromNotification", companyPhone)
            putString("jobDataFromNotification", jsonString)
            apply()
        }

        val mappedType = when (notificationType) {
            "NEW_JOB" -> "NEW_JOB"
            "APPLICATION_RECEIVED" -> "JOB_APPLIED"
            "JOB_ACCEPTANCE" -> "JOB_ACCEPTED"
            "JOB_Apply" -> "JOB_Apply"
            else -> "REFRESH_ALL"
        }

        val updateIntent = Intent("com.example.UPDATE_JOB_DATA").apply {
            putExtra("job_update_type", mappedType)
        }
        sendBroadcast(updateIntent)

        // ✅ Mostrar notificación siempre
        showNotification(
            title = dataMap["title"] ?: "Notificación",
            message = dataMap["body"] ?: "Tienes una nueva notificación",
            navigateTo = destination ?: ""
        )
    }

    private fun showNotification(title: String, message: String, navigateTo: String) {
        createNotificationChannelIfNeeded()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("navigateTo", navigateTo)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "firebase_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext).notify(1, notification)
        }
    }

    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "firebase_channel",
                "Notificaciones de Firebase",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun saveFCMToken(token: String) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("fcmToken", token)
            apply()
        }
    }

    private fun subscribeToGlobalTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("global")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Suscripción exitosa al topic 'global'")
                } else {
                    Log.e("FCM", "Error al suscribirse al topic", task.exception)
                }
            }
    }
}