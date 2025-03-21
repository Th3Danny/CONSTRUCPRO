package com.example.myapplication.notification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.notification.domain.GetNotificationsUseCase

class NotificationViewModelFactory(private val getNotificationsUseCase: GetNotificationsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotificationViewModel(getNotificationsUseCase) as T
        }
        throw IllegalArgumentException("Clase desconocida para ViewModel")
    }
}
