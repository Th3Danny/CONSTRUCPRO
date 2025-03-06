package com.example.myapplication.home.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar


@Composable
fun HomeScreen(navController: NavController, jobViewModel: JobViewModel) {
    val jobs by jobViewModel.jobs.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Jobs") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "ConstrucPro",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // ✅ Agregar pestañas para cambiar entre Jobs y Posts
        TabRow(
            selectedTabIndex = if (selectedTab == "Jobs") 0 else 1,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == "Jobs",
                onClick = { selectedTab = "Jobs" },
                text = { Text("Ofertas de Trabajo") }
            )
            Tab(
                selected = selectedTab == "Posts",
                onClick = { selectedTab = "Posts" },
                text = { Text("Publicaciones") }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Mostrar la sección correspondiente
        when (selectedTab) {
            "Jobs" -> JobScreen(navController, jobViewModel)
           // "Posts" -> PostScreen(navController) // Si hay una pantalla para Posts, úsala aquí
        }

        // ✅ Agregar la barra de navegación al final
        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}




