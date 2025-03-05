package com.example.myapplication.home.data.datasource

import com.example.myapplication.home.data.model.Message
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {
    @GET("chat/{userId}")
    suspend fun getMessages(@Path("userId") userId: String): Response<List<Message>>
}
