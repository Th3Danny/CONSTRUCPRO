package com.example.myapplication.register.data.repository

import android.util.Log
import com.example.myapplication.register.data.datasource.RegisterService
import com.example.myapplication.register.data.model.RegisterRequest


class RegisterRepository(private val registerService: RegisterService) {
    suspend fun register(request: RegisterRequest): Result<Unit> {
        return try {
            Log.d("RegisterRepository", "📡 Enviando solicitud de registro para ${request.email}")

            val response = registerService.register(request)

            if (response.isSuccessful) {
                Log.d("RegisterRepository", "✅ Registro exitoso")
                Result.success(Unit)
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Log.e("RegisterRepository", "⚠ Error en el registro: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("RegisterRepository", "🚨 Excepción en el registro: ${e.message}")
            Result.failure(e)
        }
    }
}
