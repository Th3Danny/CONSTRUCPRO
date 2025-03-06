package com.example.myapplication.register.domain

import com.example.myapplication.register.data.model.RegisterRequest
import com.example.myapplication.register.data.model.RegisterResponse
import com.example.myapplication.register.data.repository.RegisterRepository

class RegisterUseCase(private val repository: RegisterRepository) {
    suspend operator fun invoke(request: RegisterRequest): Result<Unit> {
        return repository.register(request)
    }
}
