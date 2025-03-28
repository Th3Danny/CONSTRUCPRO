package com.example.myapplication.login.data.repository

import com.example.myapplication.login.data.model.LoginRequest
import com.example.myapplication.login.data.model.LoginResponse

interface LoginRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
}