package com.example.myapplication.job.domain

import android.util.Log
import com.example.myapplication.job.data.model.Job
import com.example.myapplication.job.data.model.JobApplication
import com.example.myapplication.job.data.repository.JobRepository

class GetJobsUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(): Result<List<Job>> {
        val result = repository.getJobs()
        result.onSuccess { jobs ->
            Log.d("GetJobsUseCase", " Trabajos obtenidos: ${jobs.size}")
        }.onFailure { e ->
            Log.e("GetJobsUseCase", " Error obteniendo trabajos: ${e.message}")
        }
        return result
    }
}



class GetPendingJobsUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(): Result<List<JobApplication>> {
        return repository.getPendingJobs()
    }
}

class GetAcceptedJobsUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(): Result<List<JobApplication>> {
        return repository.getAcceptedJobs()
    }
}