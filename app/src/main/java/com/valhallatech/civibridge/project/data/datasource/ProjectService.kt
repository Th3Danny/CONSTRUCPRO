package com.valhallatech.civibridge.project.data.datasource

import com.valhallatech.civibridge.project.data.model.Project
import retrofit2.Response
import retrofit2.http.GET

interface ProjectService {
    @GET("projects")
    suspend fun getProjects(): Response<List<Project>>
}
