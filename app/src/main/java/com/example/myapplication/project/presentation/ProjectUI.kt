package com.example.myapplication.project.presentation

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
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.project.data.model.Project


@Composable
fun ProjectScreen(navController: NavController, projectViewModel: ProjectViewModel = viewModel()) {
    val projects by projectViewModel.projects.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Proyectos") }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Proyectos",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            items(projects) { project ->
                ProjectItem(project)
            }
        }

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

@Composable
fun ProjectItem(project: Project) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(project.name, color = Color(0xFFFF9800), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(project.description, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Estado: ${project.status}", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Fecha límite: ${project.deadline}", color = Color.Gray)
        }
    }
}
