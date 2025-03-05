package com.example.myapplication.home.data.datasource

import com.example.myapplication.home.data.model.Project
import retrofit2.Response
import retrofit2.http.GET

interface ProjectService {
    @GET("projects")
    suspend fun getProjects(): Response<List<Project>>
}
