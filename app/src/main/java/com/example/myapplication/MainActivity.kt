package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myapplication.core.navigation.NavigationWrapper
import com.example.myapplication.core.workers.WorkManagerScheduler
import com.example.myapplication.receiver.NetworkMonitor
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    private lateinit var networkMonitor: NetworkMonitor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 🔹 Configurar UI con Jetpack Compose
        setContent {
            MyApplicationTheme {
                NavigationWrapper()
            }
        }

        // 📌 Iniciar monitoreo de red para detectar cambios de conexión
        networkMonitor = NetworkMonitor(this)
        networkMonitor.startMonitoring()

        // 📌 Configurar Firebase Messaging para recibir notificaciones
        setupFirebaseMessaging()
    }
    override fun onDestroy() {
        super.onDestroy()
        networkMonitor.stopMonitoring()
    }

    private fun setupFirebaseMessaging() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val tokenFCM = task.result
                Log.d("FCM", "📡 Token de FCM obtenido: $tokenFCM")
                saveFCMToken(tokenFCM)
            } else {
                Log.w("FCM", "⚠ Error al obtener token de FCM", task.exception)
            }
        }
    }

    // 🔹 Guardar el token en SharedPreferences
    private fun saveFCMToken(token: String) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("fcmToken", token)
            apply()
        }
    }


}
