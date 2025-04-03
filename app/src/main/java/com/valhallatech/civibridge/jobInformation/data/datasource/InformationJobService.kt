package com.valhallatech.civibridge.jobInformation.data.datasource

import com.valhallatech.civibridge.jobInformation.data.model.ApiResponse
import com.valhallatech.civibridge.jobInformation.data.model.InformationJobRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InformationJobService {
    @GET("jobs/{jobId}")
    suspend fun getJobById(@Path("jobId") jobId: String): Response<ApiResponse<InformationJobRequest>>


}