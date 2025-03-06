package com.example.myapplication.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.home.data.model.Job
import com.example.myapplication.home.domain.GetJobsUseCase


class JobViewModel(private val getJobsUseCase: GetJobsUseCase) : ViewModel() {
    private val _jobs = MutableLiveData<List<Job>>()
    val jobs: LiveData<List<Job>> = _jobs

    init {
        fetchJobs()  // 🔹 Cargar datos al iniciar
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
}
