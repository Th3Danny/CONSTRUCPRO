package com.example.myapplication.chat.domain

import com.example.myapplication.chat.data.model.Message
import com.example.myapplication.chat.data.repository.ChatRepository

class GetMessagesUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(userId: String): Result<List<Message>> {
        return repository.getMessages(userId)
    }
}
