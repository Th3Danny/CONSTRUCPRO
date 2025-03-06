package com.example.myapplication.home.data.repository

import android.content.Context
import android.util.Log
import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.home.data.model.Notification

class NotificationRepository(private val context: Context) {
    private val notificationService = RetrofitHelper.notificationService

    suspend fun getNotifications(): Result<List<Notification>> {
        return try {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)

            if (userId == -1) {
                Log.e("NotificationRepository", "🚨 No se encontró userId en SharedPreferences")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            val response = notificationService.getNotifications(userId)

            if (response.isSuccessful) {
                val notificationResponse = response.body()
                if (notificationResponse != null) {
                    Log.d("NotificationRepository", "✅ Notificaciones obtenidas: ${notificationResponse.data.size}")
                    Result.success(notificationResponse.data)
                } else {
                    Log.e("NotificationRepository", "⚠ Respuesta vacía del servidor")
                    Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                Log.e("NotificationRepository", "⚠ Error en la respuesta de la API")
                Result.failure(Exception("Error en la respuesta de la API"))
            }
        } catch (e: Exception) {
            Log.e("NotificationRepository", "🚨 Excepción al obtener notificaciones: ${e.message}")
            Result.failure(e)
        }
    }
}


