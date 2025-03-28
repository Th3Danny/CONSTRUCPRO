package com.example.myapplication.jobInformation.data.datasource

import com.example.myapplication.jobInformation.data.model.InformationJobRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InformationJobService {
    @GET("jobs/{id}")
    suspend fun getJobById(@Path("id") jobId: String): Response<InformationJobRequest>

}