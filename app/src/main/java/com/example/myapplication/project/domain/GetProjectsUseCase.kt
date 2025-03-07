package com.example.myapplication.project.domain

import com.example.myapplication.project.data.model.Project
import com.example.myapplication.project.data.repository.ProjectRepository

class GetProjectsUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(): Result<List<Project>> {
        return repository.getProjects()
    }
}
