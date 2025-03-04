package com.example.myapplication.login.domain

import com.example.myapplication.login.data.model.LoginRequest
import com.example.myapplication.login.data.model.LoginResponse
import com.example.myapplication.login.data.repository.AuthRepository


class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest): Result<LoginResponse> {
        return try {
            repository.login(request)
        } catch (e: Exception) {
            // Manejo de excepciones
            Result.failure(e)
        }
    }
}

