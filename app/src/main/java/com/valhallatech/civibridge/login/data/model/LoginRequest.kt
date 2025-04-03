package com.valhallatech.civibridge.login.data.model

data class LoginRequest(
    val email: String,
    val password: String,
    val fcm_token: String
)

