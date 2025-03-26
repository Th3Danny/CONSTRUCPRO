package com.example.myapplication.job.domain

import com.example.myapplication.job.data.repository.JobRepository


class PostJobsUseCase(private val jobRepository: JobRepository) {
    suspend operator fun invoke(jobId: Int, applicantId: Int): Result<Boolean> {
        return jobRepository.applyForJob(jobId, applicantId)
    }
}