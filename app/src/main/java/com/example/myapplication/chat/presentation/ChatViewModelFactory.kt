package com.example.myapplication.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.chat.domain.GetMessagesUseCase

class ChatViewModelFactory(private val getMessagesUseCase: GetMessagesUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(getMessagesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
