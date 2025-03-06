package com.example.myapplication.home.presentation

import JobItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar



@Composable
fun JobScreen(navController: NavController, jobViewModel: JobViewModel) {
    val jobs by jobViewModel.jobs.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Jobs") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ofertas de Trabajo",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 16.dp)
        ) {
            items(jobs) { job ->
                JobItem(job) // ✅ Usa JobItem aquí
            }
        }

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}



