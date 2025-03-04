package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProjectScreen(
    projectViewModel: ProjectViewModel = viewModel()
) {
    val projects by projectViewModel.projects.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        projectViewModel.fetchProjects()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Proyectos", color = Color(0xFFFF9800), style = MaterialTheme.typography.titleLarge)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(projects) { project ->
                ProjectItem(project)
            }
        }
    }
}

@Composable
fun ProjectItem(project: com.example.myapplication.home.data.model.Project) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = project.name, color = Color(0xFFFF9800), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = project.description, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Estado: ${project.status}", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fecha límite: ${project.deadline}", color = Color.Gray)
        }
    }
}
