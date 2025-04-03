package com.valhallatech.civibridge.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valhallatech.civibridge.profile.domain.ProfileUseCase

class ProfileViewModelFactory(
    private val useCase: ProfileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

