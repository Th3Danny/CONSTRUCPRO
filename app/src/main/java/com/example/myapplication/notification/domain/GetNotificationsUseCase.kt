package com.example.myapplication.notification.domain

import com.example.myapplication.notification.data.model.Notification
import com.example.myapplication.notification.data.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return repository.getNotifications()
    }
}