package com.example.myapplication.jobInformation.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.jobInformation.data.model.InformationJobRequest

class InformationJobRepository {
    private val infoService = RetrofitHelper.informationJobService

    suspend fun getJobById(jobId: String): Result<InformationJobRequest> {
        return try {
            val response = infoService.getJobById(jobId)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                val jobInfo = apiResponse?.data
                if (jobInfo != null) {
                    Result.success(jobInfo)
                } else {
                    Result.failure(Exception("No se encontró la información del trabajo"))
                }
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

