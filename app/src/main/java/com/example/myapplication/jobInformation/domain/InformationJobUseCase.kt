package com.example.myapplication.jobInformation.domain

import com.example.myapplication.jobInformation.data.model.InformationJobRequest
import com.example.myapplication.jobInformation.data.repository.InformationJobRepository

class InformationJobUseCase(private val repository: InformationJobRepository) {
    suspend operator fun invoke(jobId: String): Result<InformationJobRequest> {
        return repository.getJobById(jobId)
    }
}