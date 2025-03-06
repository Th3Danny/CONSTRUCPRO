package com.example.myapplication.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.home.domain.GetJobsUseCase

class JobViewModelFactory(private val getJobsUseCase: GetJobsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobViewModel::class.java)) {
            return JobViewModel(getJobsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


