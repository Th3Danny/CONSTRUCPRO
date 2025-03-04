package com.example.myapplication.home.domain

import com.example.myapplication.home.data.model.Message
import com.example.myapplication.home.data.repository.ChatRepository

class GetMessagesUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(userId: String): Result<List<Message>> {
        return repository.getMessages(userId)
    }
}
