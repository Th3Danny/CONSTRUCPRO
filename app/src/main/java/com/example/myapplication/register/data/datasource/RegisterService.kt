package com.example.myapplication.register.data.datasource

import com.example.myapplication.register.data.model.RegisterRequest
import com.example.myapplication.register.data.model.RegisterResponse
import com.google.firebase.firestore.auth.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("users")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}