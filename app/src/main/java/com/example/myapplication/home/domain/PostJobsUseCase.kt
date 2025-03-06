package com.example.myapplication.home.domain

import com.example.myapplication.home.data.repository.JobRepository


class PostJobsUseCase(private val jobRepository: JobRepository) {
    suspend operator fun invoke(jobId: Int): Result<Boolean> {
        return jobRepository.applyForJob(jobId)
    }
}