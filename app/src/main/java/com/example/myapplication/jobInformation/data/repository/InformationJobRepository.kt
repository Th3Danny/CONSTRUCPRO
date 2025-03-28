package com.example.myapplication.jobInformation.data.repository

import com.example.myapplication.core.network.RetrofitHelper
import com.example.myapplication.jobInformation.data.model.InformationJobRequest

class InformationJobRepository {
    private val infoService = RetrofitHelper.informationJobService

    suspend fun getJobById(jobId: String): Result<InformationJobRequest> {
        return try {
            val response = infoService.getJobById(jobId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Respuesta vacía"))
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
