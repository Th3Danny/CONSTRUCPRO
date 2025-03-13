package com.example.myapplication.job.data.datasource

import com.example.myapplication.job.data.model.JobApplicationRequest
import com.example.myapplication.job.data.model.JobApplicationResponse
import com.example.myapplication.job.data.model.JobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JobsService {
    @GET("jobs/user/{userId}")
    suspend fun getJobs(@Path("userId") userId: Int): Response<JobResponse>

    @GET("job-applications/user/{userId}/pending")
    suspend fun getPendingApplications(@Path("userId") userId: Int): Response<JobApplicationResponse>

    @GET("job-applications/user/{userId}/accepted")
    suspend fun getAcceptedApplications(@Path("userId") userId: Int): Response<JobApplicationResponse>
}

interface JobService {
    @POST("job-applications")
    suspend fun applyToJob(@Body requestBody: JobApplicationRequest): Response<Unit>
}

