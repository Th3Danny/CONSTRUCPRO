package com.valhallatech.civibridge.job.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valhallatech.civibridge.job.data.model.Job
import com.valhallatech.civibridge.job.data.model.JobApplication
import com.valhallatech.civibridge.job.domain.GetAcceptedJobsUseCase
import com.valhallatech.civibridge.job.domain.GetJobsUseCase
import com.valhallatech.civibridge.job.domain.GetPendingJobsUseCase
import com.valhallatech.civibridge.job.domain.PostJobsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JobViewModel(
    private val context: Context,
    private val getJobsUseCase: GetJobsUseCase,
    private val getPendingJobsUseCase: GetPendingJobsUseCase,
    private val getAcceptedJobsUseCase: GetAcceptedJobsUseCase,
    private val postJobsUseCase: PostJobsUseCase
) : ViewModel() {

    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    val jobs: StateFlow<List<Job>> = _jobs.asStateFlow()

    private val _pendingJobs = MutableStateFlow<List<JobApplication>>(emptyList())
    val pendingJobs: StateFlow<List<JobApplication>> = _pendingJobs.asStateFlow()

    private val _acceptedJobs = MutableStateFlow<List<JobApplication>>(emptyList())
    val acceptedJobs: StateFlow<List<JobApplication>> = _acceptedJobs.asStateFlow()

    private val _applicationSuccess = MutableStateFlow<Boolean?>(null)
    val applicationSuccess: StateFlow<Boolean?> = _applicationSuccess.asStateFlow()

    init {
        fetchJobs()
        fetchPendingJobs()
        fetchAcceptedJobs()
    }

    fun fetchJobs() {
        viewModelScope.launch {
            val result = getJobsUseCase()
            result.onSuccess { jobList ->
                Log.d("JobViewModel", " Trabajos actualizados en StateFlow: ${jobList.size}")
                _jobs.value = jobList
            }.onFailure { e ->
                Log.e("JobViewModel", " Error actualizando StateFlow: ${e.message}")
                _jobs.value = emptyList()
            }
        }
    }

    fun fetchPendingJobs() {
        viewModelScope.launch {
            val result = getPendingJobsUseCase()
            result.onSuccess { pendingList ->
                _pendingJobs.value = pendingList
            }.onFailure {
                _pendingJobs.value = emptyList()
            }
        }
    }

    fun fetchAcceptedJobs() {
        viewModelScope.launch {
            val result = getAcceptedJobsUseCase()
            result.onSuccess { acceptedList ->
                _acceptedJobs.value = acceptedList
            }.onFailure {
                _acceptedJobs.value = emptyList()
            }
        }
    }

    fun applyToJob(jobId: Int, applicantId: Int) {
        viewModelScope.launch {
            val result = postJobsUseCase(jobId, applicantId)
            result.onSuccess {
                _applicationSuccess.value = true
                fetchPendingJobs()
            }.onFailure {
                _applicationSuccess.value = false
            }
        }
    }

    fun refreshJobs() {
        fetchJobs()
    }

    fun refreshPendingJobs() {
        fetchPendingJobs()
    }

    fun refreshAcceptedJobs() {
        fetchAcceptedJobs()
    }

    fun refreshApplyJobs() {
        fetchPendingJobs()
        fetchAcceptedJobs()
    }
}
