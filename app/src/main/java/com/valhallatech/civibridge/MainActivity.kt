package com.valhallatech.civibridge

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.valhallatech.civibridge.core.navigation.NavigationWrapper
import com.valhallatech.civibridge.core.service.NetworkMonitorService
import com.valhallatech.civibridge.ui.theme.MyApplicationTheme
import com.google.firebase.messaging.FirebaseMessaging
import android.Manifest

class MainActivity : ComponentActivity() {

    private val NOTIFICATION_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNotificationPermission()

        val serviceIntent = Intent(this, NetworkMonitorService::class.java)
        startForegroundService(serviceIntent)

        setupFirebaseMessaging()

        val destination = intent.getStringExtra("navigateTo")
        val jobId = intent.getStringExtra("jobId")
        getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE).edit().apply {
            putString("navigateTo", destination)
            putString("jobIdFromNotification", jobId)
            apply()
        }

        setContent {
            MyApplicationTheme {
                NavigationWrapper()
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), NOTIFICATION_PERMISSION_CODE)
            }
        }
    }

    private fun setupFirebaseMessaging() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val tokenFCM = task.result
                Log.d("FCM", "Token de FCM obtenido: $tokenFCM")
                saveFCMToken(tokenFCM)
            } else {
                Log.w("FCM", "Error al obtener token de FCM", task.exception)
            }
        }
    }

    private fun saveFCMToken(token: String) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("fcmToken", token)
            apply()
        }
    }
}
