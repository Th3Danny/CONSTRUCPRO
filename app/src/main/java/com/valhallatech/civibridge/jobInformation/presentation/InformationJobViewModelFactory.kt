package com.valhallatech.civibridge.jobInformation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valhallatech.civibridge.jobInformation.domain.InformationJobUseCase

class InformationJobViewModelFactory(
    private val useCase: InformationJobUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationJobViewModel::class.java)) {
            return InformationJobViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


