package com.example.myapplication.login.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.login.domain.LoginUseCase


class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginUseCase, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

