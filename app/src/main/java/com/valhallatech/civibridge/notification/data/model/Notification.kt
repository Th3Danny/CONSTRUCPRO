package com.valhallatech.civibridge.notification.data.model

data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val sent_at: String,
    val data: String? // <-- Este campo es un JSON string
)

data class NotificationData(
    val jobTitle: String?,
    val companyName: String?,
    val timestamp: String?,
    val jobId: String?,
    val companyPhone: String?,
    val notificationType: String?,
    val navigateTo: String?
)

