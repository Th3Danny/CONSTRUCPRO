package com.valhallatech.civibridge.utils

import com.google.gson.Gson
import com.valhallatech.civibridge.notification.data.model.NotificationData

fun parseNotificationData(json: String?): NotificationData? {
    return try {
        json?.let {
            Gson().fromJson(it, NotificationData::class.java)
        }
    } catch (e: Exception) {
        null
    }
}

