package com.example.myapplication.notification.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.notification.data.model.Notification
import com.example.myapplication.notification.domain.GetNotificationsUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(private val getNotificationsUseCase: GetNotificationsUseCase) : ViewModel() {

    private val _notifications = MutableLiveData<List<Notification>>()
    val notifications: LiveData<List<Notification>> = _notifications

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            try {
                val result = getNotificationsUseCase()
                _notifications.value = result.getOrDefault(emptyList())
            } catch (e: Exception) {
                _notifications.value = emptyList()
            }
        }
    }
}
