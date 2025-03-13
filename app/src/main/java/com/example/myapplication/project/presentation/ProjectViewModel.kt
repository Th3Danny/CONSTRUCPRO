package com.example.myapplication.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.project.domain.GetProjectsUseCase
import com.example.myapplication.project.data.model.Project

class ProjectViewModel(private val getProjectsUseCase: GetProjectsUseCase) : ViewModel() {

    private val _projects = MutableLiveData<List<Project>>(emptyList())
    val projects: LiveData<List<Project>> = _projects

    init {
        fetchProjects()
    }

    fun fetchProjects() {
        viewModelScope.launch {
            val result = getProjectsUseCase()
            result.onSuccess { _projects.value = it }
                .onFailure { _projects.value = emptyList() }
        }
    }
}