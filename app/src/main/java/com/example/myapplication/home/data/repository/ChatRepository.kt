package com.example.myapplication.home.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.home.data.model.Message

class ChatRepository {
    private val chatService = RetrofitHelper.chatService

    suspend fun getMessages(userId: String): Result<List<Message>> {
        return try {
            val response = chatService.getMessages(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Respuesta vacía del servidor"))
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
