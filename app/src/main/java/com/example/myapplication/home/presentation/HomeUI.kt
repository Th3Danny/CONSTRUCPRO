package com.example.myapplication.home.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.example.myapplication.components.footer.BottomNavigationBar
import com.example.myapplication.job.presentation.JobScreen
import com.example.myapplication.job.presentation.JobViewModel
import com.example.myapplication.login.presentation.LoginViewModel


@Composable
fun HomeScreen(navController: NavController, jobViewModel: JobViewModel, loginViewModel: LoginViewModel) {
    val jobs by jobViewModel.jobs.collectAsState()
    var selectedTab by remember { mutableStateOf("Jobs") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "ConstrucPro",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

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

        when (selectedTab) {
            "Jobs" -> JobScreen(navController, jobViewModel, loginViewModel)
        }

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}





