package com.example.myapplication.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.myapplication.login.data.repository.AuthRepository
import com.example.myapplication.login.domain.LoginUseCase
import com.example.myapplication.login.presentation.LoginViewModel
import com.example.myapplication.login.presentation.LoginViewModelFactory

object LoginViewModelProvider {
    fun provide(context: Context, owner: ViewModelStoreOwner): LoginViewModel {
        val loginUseCase = LoginUseCase(AuthRepository)
        val factory = LoginViewModelFactory(loginUseCase, context)
        return ViewModelProvider(owner, factory)[LoginViewModel::class.java]
    }
}