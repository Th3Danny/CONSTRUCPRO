package com.valhallatech.civibridge.login.data.datasource

import com.valhallatech.civibridge.login.data.model.LoginRequest
import com.valhallatech.civibridge.login.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginService {
    @POST("auth/authenticate")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}


