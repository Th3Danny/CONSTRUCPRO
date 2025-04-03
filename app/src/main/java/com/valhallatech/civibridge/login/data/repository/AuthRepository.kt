package com.valhallatech.civibridge.login.data.repository

import com.valhallatech.civibridge.core.network.RetrofitHelper
import com.valhallatech.civibridge.login.data.model.LoginRequest
import com.valhallatech.civibridge.login.data.model.LoginResponse

object AuthRepository : LoginRepository {
    private val loginService = RetrofitHelper.loginService

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
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
