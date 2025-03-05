package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.home.data.model.Project


@Composable
fun ProjectScreen(navController: NavController) {
    // 🔹 Datos de prueba corregidos
    val fakeProjects = listOf(
        Project(id = "1", name = "Centro Comercial", description = "Construcción de 10 pisos", author = "Juan Pérez"),
        Project(id = "2", name = "Edificio Corporativo", description = "Modernización del edificio central", author = "María López")
    )

    var selectedTab by remember { mutableStateOf("Proyectos") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
            items(fakeProjects) { project ->
                ProjectItem(project)
            }
        }
        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

@Composable
fun ProjectItem(project: Project) {
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
            Text(text = "Autor: ${project.author}", color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Estado: ${project.status}", color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Fecha límite: ${project.deadline}", color = Color.Gray)
        }
    }
}
