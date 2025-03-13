package com.example.myapplication.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.project.domain.GetProjectsUseCase

class ProjectViewModelFactory(private val getProjectsUseCase: GetProjectsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(getProjectsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
