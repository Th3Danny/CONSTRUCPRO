package com.example.myapplication.home.domain

import com.example.myapplication.home.data.model.Notification
import com.example.myapplication.home.data.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return repository.getNotifications()
    }
}