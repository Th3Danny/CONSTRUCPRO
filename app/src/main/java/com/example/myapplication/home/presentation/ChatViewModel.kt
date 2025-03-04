package com.example.myapplication.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.home.domain.GetMessagesUseCase
import com.example.myapplication.home.data.model.Message

class ChatViewModel(private val getMessagesUseCase: GetMessagesUseCase) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    fun fetchMessages(userId: String) {
        viewModelScope.launch {
            getMessagesUseCase(userId).onSuccess { _messages.value = it }
        }
    }
}
