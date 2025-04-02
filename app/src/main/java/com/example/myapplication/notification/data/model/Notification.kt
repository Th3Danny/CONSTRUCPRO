package com.example.myapplication.notification.data.model

data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val sent_at: String,
    val data: String? // <-- Este campo es un JSON string
)

data class NotificationData(
    val jobId: String?,
    val companyPhone: String?,
    val companyName: String?,
    val jobTitle: String?,
    val navigateTo: String?,
    val notificationType: String?,
    val timestamp: String?
)
