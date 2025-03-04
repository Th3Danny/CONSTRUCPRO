package com.example.myapplication.home.domain

import com.example.myapplication.home.data.model.Project
import com.example.myapplication.home.data.repository.ProjectRepository

class GetProjectsUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(): Result<List<Project>> {
        return repository.getProjects()
    }
}
