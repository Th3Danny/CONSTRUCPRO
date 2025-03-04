package com.example.myapplication.login.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.login.data.model.LoginRequest
import com.example.myapplication.login.data.model.LoginResponse

class AuthRepository {
    private val loginService = RetrofitHelper.loginService

    suspend fun login(request: LoginRequest) : Result<LoginResponse> {
        return try {
            val response = loginService.login(request)

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
