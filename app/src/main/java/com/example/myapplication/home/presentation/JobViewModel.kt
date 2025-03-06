package com.example.myapplication.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.home.data.model.Job
import com.example.myapplication.home.data.model.JobApplication
import com.example.myapplication.home.domain.GetAcceptedJobsUseCase
import com.example.myapplication.home.domain.GetJobsUseCase
import com.example.myapplication.home.domain.GetPendingJobsUseCase
import com.example.myapplication.home.domain.PostJobsUseCase


class JobViewModel(
    private val getJobsUseCase: GetJobsUseCase,
    private val getPendingJobsUseCase: GetPendingJobsUseCase,
    private val getAcceptedJobsUseCase: GetAcceptedJobsUseCase,
    private val postJobsUseCase: PostJobsUseCase

) : ViewModel() {

    private val _jobs = MutableLiveData<List<Job>>()
    val jobs: LiveData<List<Job>> = _jobs

    private val _pendingJobs = MutableLiveData<List<JobApplication>>()
    val pendingJobs: LiveData<List<JobApplication>> = _pendingJobs

    private val _acceptedJobs = MutableLiveData<List<JobApplication>>() // ✅ Ahora almacenamos aplicaciones aceptadas
    val acceptedJobs: LiveData<List<JobApplication>> = _acceptedJobs

    private val _applicationSuccess = MutableLiveData<Boolean>()
    val applicationSuccess: LiveData<Boolean> = _applicationSuccess

    init {
        fetchJobs()
        fetchPendingJobs()
        fetchAcceptedJobs()
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            val result = getJobsUseCase()
            result.onSuccess { jobList ->
                Log.d("JobViewModel", "✅ Trabajos actualizados en LiveData: ${jobList.size}")
                _jobs.value = jobList
            }.onFailure { e ->
                Log.e("JobViewModel", "🚨 Error actualizando LiveData: ${e.message}")
                _jobs.value = emptyList()
            }
        }
    }

    private fun fetchPendingJobs() { // ✅ Función para traer trabajos pendientes
        viewModelScope.launch {
            val result = getPendingJobsUseCase()
            result.onSuccess { pendingList ->
                _pendingJobs.value = pendingList
            }.onFailure {
                _pendingJobs.value = emptyList()
            }
        }
    }

    private fun fetchAcceptedJobs() { // ✅ Función para traer trabajos aceptados
        viewModelScope.launch {
            val result = getAcceptedJobsUseCase()
            result.onSuccess { acceptedList ->
                _acceptedJobs.value = acceptedList
            }.onFailure {
                _acceptedJobs.value = emptyList()
            }
        }
    }

    fun applyToJob(jobId: Int) {
        viewModelScope.launch {
            val result = postJobsUseCase(jobId)
            result.onSuccess {
                _applicationSuccess.value = true
                fetchPendingJobs() // ✅ Actualizar lista de pendientes tras aplicar
            }.onFailure {
                _applicationSuccess.value = false
            }
        }
    }
}

