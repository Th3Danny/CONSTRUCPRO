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

// Modelo de datos falsos
data class Post(val title: String, val description: String, val author: String)

@Composable
fun HomeScreen(navController: NavController) {
    val fakePosts = listOf(
        Post("Construcción de puente", "Avance del puente en la zona centro", "Juan Pérez"),
        Post("Nuevo rascacielos", "Iniciando cimentación del edificio", "María López"),
        Post("Reparaciones viales", "Mejoras en las calles del centro", "Carlos Méndez")
    )

    var selectedTab by remember { mutableStateOf("Publicaciones") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
            items(fakePosts) { post -> PostItem(post) }
        }
        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}


// 🔹 Composable para mostrar una publicación
@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(post.title, color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(post.description, color = Color.LightGray, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Autor: ${post.author}", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
        }
    }
}
