package com.example.myapplication.job.presentation

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.job.data.model.JobApplication


@Composable
fun JobScreen(navController: NavController, jobViewModel: JobViewModel) {
    val jobs by jobViewModel.jobs.observeAsState(emptyList())
    val pendingJobs by jobViewModel.pendingJobs.observeAsState(emptyList())
    val acceptedJobs by jobViewModel.acceptedJobs.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Ofertas") }

    // ✅ Obtener contexto una sola vez
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE) }
    val applicantId = remember { sharedPreferences.getInt("userId", -1) } // ✅ Guardar en remember para evitar múltiples llamadas

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ofertas de Trabajo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Selector de pestañas
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TabButton("Ofertas", selectedTab) { selectedTab = "Ofertas" }
            TabButton("Pendientes", selectedTab) { selectedTab = "Pendientes" }
            TabButton("Aceptados", selectedTab) { selectedTab = "Aceptados" }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            when (selectedTab) {
                "Ofertas" -> {
                    items(jobs) { job ->
                        JobItem(job) { jobId ->
                            if (applicantId != -1) {
                                jobViewModel.applyToJob(jobId, applicantId) // ✅ Ahora pasamos ambos valores
                            } else {
                                Log.e("JobScreen", "🚨 Usuario no autenticado")
                            }
                        }
                    }
                }
                "Pendientes" -> {
                    items(pendingJobs) { jobApplication ->
                        JobApplicationItem(jobApplication)
                    }
                }
                "Aceptados" -> {
                    items(acceptedJobs) { jobApplication ->
                        JobApplicationItem(jobApplication)
                    }
                }
            }
        }


        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}


@Composable
fun TabButton(text: String, selectedTab: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedTab == text) Color(0xFFFF9800) else Color.DarkGray
        ),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(text, color = Color.White)
    }
}

@Composable
fun JobApplicationItem(jobApplication: JobApplication) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = jobApplication.job_title,
                color = Color(0xFFFF9800),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Estado: ${jobApplication.status}",
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📅 Aplicado el: ${jobApplication.applied_at}",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}






