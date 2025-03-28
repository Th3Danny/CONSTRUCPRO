package com.example.myapplication.jobInformation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.jobInformation.domain.InformationJobUseCase

class InformationJobViewModelFactory(private val getInformationUseCase: InformationJobUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationJobViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InformationJobViewModel(getInformationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

