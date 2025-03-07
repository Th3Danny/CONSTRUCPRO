package com.example.myapplication.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.chat.domain.GetMessagesUseCase
import com.example.myapplication.chat.data.model.Message

class ChatViewModel(private val getMessagesUseCase: GetMessagesUseCase) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> = _messages

    fun fetchMessages(userId: String) {
        viewModelScope.launch {
            val result = getMessagesUseCase(userId)
            result.onSuccess { _messages.value = it }
                .onFailure { _messages.value = emptyList() }
        }
    }
}
