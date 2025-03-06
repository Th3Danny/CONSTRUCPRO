package com.example.myapplication.register.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.register.data.datasource.RegisterService
import com.example.myapplication.register.data.model.RegisterRequest
import com.example.myapplication.register.data.model.RegisterResponse

class RegisterRepository(private val registerService: RegisterService) {
    suspend fun register(request: RegisterRequest): Result<Unit> {
        return try {
            val response = registerService.register(request) // Llamar al método correcto
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Error en el registro"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
