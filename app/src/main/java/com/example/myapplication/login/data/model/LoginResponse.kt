package com.example.myapplication.login.data.model

data class LoginResponse(
    val success: Boolean,
    val id_user: Int,
    val token: String?,   // Token JWT si el login es exitoso
    val message: String?  // Mensaje en caso de error
)
