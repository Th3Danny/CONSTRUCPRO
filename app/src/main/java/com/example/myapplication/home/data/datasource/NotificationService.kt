package com.example.myapplication.home.data.datasource


import com.example.myapplication.home.data.model.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface NotificationService {
    @GET("notificationsLog/user/{userId}")
    suspend fun getNotifications(@Path("userId") userId: Int): Response<NotificationResponse>
}

