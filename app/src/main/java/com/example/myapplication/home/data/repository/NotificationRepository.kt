package com.example.myapplication.home.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.home.data.model.Notification

class NotificationRepository {
    private val notificationService = RetrofitHelper.notificationService

    suspend fun getNotifications(): Result<List<Notification>> {
        return try {
            val response = notificationService.getNotifications()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error en la respuesta de la API"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}