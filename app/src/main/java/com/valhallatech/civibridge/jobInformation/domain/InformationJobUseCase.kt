package com.valhallatech.civibridge.jobInformation.domain

import com.valhallatech.civibridge.jobInformation.data.model.InformationJobRequest
import com.valhallatech.civibridge.jobInformation.data.repository.InformationJobRepository

class InformationJobUseCase(private val repository: InformationJobRepository) {
    suspend fun getJobById(jobId: String): Result<InformationJobRequest> {
        return repository.getJobById(jobId)
    }

}