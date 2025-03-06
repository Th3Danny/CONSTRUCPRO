package com.example.myapplication.home.data.datasource

import com.example.myapplication.home.data.model.JobResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("jobs")
    suspend fun getJobs(): Response<JobResponse>
}
