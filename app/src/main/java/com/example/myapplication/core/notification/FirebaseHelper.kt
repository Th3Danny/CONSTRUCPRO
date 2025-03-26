package com.example.myapplication.core.notification

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

object FirebaseHelper {

    fun sendTokenToServer(context: Context, tokenFCM: String) {
        Log.d("FCM", "📡 Intentando enviar token FCM al backend: $tokenFCM")

        // 🔹 Recuperamos el token de autenticación desde SharedPreferences
        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val authToken = sharedPreferences.getString("authToken", "") ?: ""

        if (authToken.isEmpty()) {
            Log.e("FCM", " No hay token de autenticación guardado, no se enviará el token FCM.")
            return
        }

        val url = "http://0.tcp.ngrok.io:17026/api/token"

        val json = JSONObject().apply {
            put("token", tokenFCM)
        }

        val body = RequestBody.create("application/json".toMediaTypeOrNull(), json.toString())

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $authToken") // Token dinámico
            .post(body)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("FCM", "🚨 Error enviando token al backend: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("FCM", " Token de FCM enviado con éxito al backend")
                } else {
                    Log.e("FCM", " Fallo al enviar token: Código HTTP ${response.code} - ${response.message}")
                    Log.e("FCM", " Respuesta del servidor: ${response.body?.string()}")
                }
            }
        })
    }
}
