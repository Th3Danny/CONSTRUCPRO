package com.valhallatech.civibridge.job.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.valhallatech.civibridge.job.domain.GetAcceptedJobsUseCase
import com.valhallatech.civibridge.job.domain.GetJobsUseCase
import com.valhallatech.civibridge.job.domain.GetPendingJobsUseCase
import com.valhallatech.civibridge.job.domain.PostJobsUseCase

class JobViewModelFactory(
    private val context: Context,
    private val getJobsUseCase: GetJobsUseCase,
    private val getPendingJobsUseCase: GetPendingJobsUseCase,
    private val getAcceptedJobsUseCase: GetAcceptedJobsUseCase,
    private val postJobsUseCase: PostJobsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JobViewModel::class.java)) {
            return JobViewModel(context, getJobsUseCase, getPendingJobsUseCase, getAcceptedJobsUseCase, postJobsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}





