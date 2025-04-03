package com.valhallatech.civibridge.login.data.repository

import com.valhallatech.civibridge.login.data.model.LoginRequest
import com.valhallatech.civibridge.login.data.model.LoginResponse

interface LoginRepository {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
}