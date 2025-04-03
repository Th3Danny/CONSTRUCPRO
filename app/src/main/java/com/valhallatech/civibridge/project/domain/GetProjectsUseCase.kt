package com.valhallatech.civibridge.project.domain

import com.valhallatech.civibridge.project.data.model.Project
import com.valhallatech.civibridge.project.data.repository.ProjectRepository

class GetProjectsUseCase(private val repository: ProjectRepository) {
    suspend operator fun invoke(): Result<List<Project>> {
        return repository.getProjects()
    }
}
