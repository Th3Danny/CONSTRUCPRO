package com.example.myapplication.profile.presentation.components
import com.example.myapplication.profile.data.model.CompleteProfileData

data class ProfileUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: CompleteProfileData? = null
)