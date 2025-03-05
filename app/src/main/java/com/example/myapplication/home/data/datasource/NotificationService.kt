package com.example.myapplication.home.data.datasource

import com.example.myapplication.home.data.model.Notification
import retrofit2.Response
import retrofit2.http.GET

interface NotificationService {
    @GET("notifications")
    suspend fun getNotifications(): Response<List<Notification>>
}