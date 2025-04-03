package com.valhallatech.civibridge.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.valhallatech.civibridge.login.data.repository.AuthRepository
import com.valhallatech.civibridge.login.domain.LoginUseCase
import com.valhallatech.civibridge.login.presentation.LoginViewModel
import com.valhallatech.civibridge.login.presentation.LoginViewModelFactory

object LoginViewModelProvider {
    fun provide(context: Context, owner: ViewModelStoreOwner): LoginViewModel {
        val loginUseCase = LoginUseCase(AuthRepository)
        val factory = LoginViewModelFactory(loginUseCase, context)
        return ViewModelProvider(owner, factory)[LoginViewModel::class.java]
    }
}