package com.valhallatech.civibridge.profile.presentation.components
import com.valhallatech.civibridge.profile.data.model.CompleteProfileData

data class ProfileUIState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: CompleteProfileData? = null
)