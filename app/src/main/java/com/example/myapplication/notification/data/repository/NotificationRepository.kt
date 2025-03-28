package com.example.myapplication.notification.data.repository

import android.content.Context
import android.util.Log
import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.notification.data.model.Notification
import com.example.myapplication.notification.data.model.NotificationResponse

class NotificationRepository(private val context: Context) {
    private val notificationService = RetrofitHelper.notificationService

    suspend fun getNotifications(): Result<List<Notification>> {
        return try {
            val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("userId", -1)

            //  Log para verificar el userId guardado
            Log.d("NotificationRepository", " Recuperando userId desde SharedPreferences: $userId")

            //  Verificar que userId sea válido
            if (userId == -1) {
                Log.e("NotificationRepository", " No se encontró userId en SharedPreferences")
                return Result.failure(Exception("Usuario no autenticado"))
            }

            Log.d("NotificationRepository", "📡 Obteniendo notificaciones para userId: $userId")

            val response = notificationService.getNotifications(userId)

            if (response.isSuccessful) {
                val notificationResponse: NotificationResponse? = response.body()
                if (notificationResponse != null) {
                    Log.d("NotificationRepository", "Notificaciones obtenidas: ${notificationResponse.data.size} para userId: $userId")
                    return Result.success(notificationResponse.data)
                } else {
                    Log.e("NotificationRepository", " Respuesta vacía del servidor para userId: $userId")
                    return Result.failure(Exception("Respuesta vacía del servidor"))
                }
            } else {
                Log.e("NotificationRepository", " Error en la respuesta de la API para userId: $userId - ${response.errorBody()?.string()}")
                return Result.failure(Exception("Error en la respuesta de la API"))
            }
        } catch (e: Exception) {
            Log.e("NotificationRepository", " Excepción al obtener notificaciones para userId: ${e.message}")
            return Result.failure(e)
        }
    }

}

