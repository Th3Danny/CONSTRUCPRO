package com.example.myapplication.home.domain

import android.util.Log
import com.example.myapplication.home.data.model.Job
import com.example.myapplication.home.data.repository.JobRepository

class GetJobsUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(): Result<List<Job>> {
        val result = repository.getJobs()
        result.onSuccess { jobs ->
            Log.d("GetJobsUseCase", "✅ Trabajos obtenidos: ${jobs.size}")
        }.onFailure { e ->
            Log.e("GetJobsUseCase", "🚨 Error obteniendo trabajos: ${e.message}")
        }
        return result
    }
}

