package com.example.myapplication.register.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.register.data.model.RegisterRequest
import com.example.myapplication.register.data.model.RegisterResponse

class RegisterRepository {
    private val registerService = RetrofitHelper.registerService

    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = registerService.register(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string() ?: "Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
