package com.valhallatech.civibridge.notification.domain

import com.valhallatech.civibridge.notification.data.model.Notification
import com.valhallatech.civibridge.notification.data.repository.NotificationRepository

class GetNotificationsUseCase(private val repository: NotificationRepository) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return repository.getNotifications()
    }
}