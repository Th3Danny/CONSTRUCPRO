package com.example.myapplication.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.home.domain.GetAcceptedJobsUseCase
import com.example.myapplication.home.domain.GetJobsUseCase
import com.example.myapplication.home.domain.GetPendingJobsUseCase
import com.example.myapplication.home.domain.PostJobsUseCase

class JobViewModelFactory(
    private val getJobsUseCase: GetJobsUseCase,
    private val getPendingJobsUseCase: GetPendingJobsUseCase,
    private val getAcceptedJobsUseCase: GetAcceptedJobsUseCase,
    private val postJobsUseCase: PostJobsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobViewModel::class.java)) {
            return JobViewModel(getJobsUseCase, getPendingJobsUseCase, getAcceptedJobsUseCase, postJobsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}





