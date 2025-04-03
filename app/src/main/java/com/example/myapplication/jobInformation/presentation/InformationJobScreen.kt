package com.example.myapplication.jobInformation.presentation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.components.footer.BottomNavigationBar
import com.example.myapplication.jobInformation.data.model.InformationJobRequest
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.example.myapplication.jobInformation.presentation.componets.ErrorJobInformation
import com.example.myapplication.jobInformation.presentation.componets.JobInformationItem
import com.example.myapplication.jobInformation.presentation.componets.LoadingJobInformation
import com.example.myapplication.utils.parseNotificationData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobInformationScreen(
    navController: NavController,
    jobId: String? = null,
    viewModel: InformationJobViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Trabajos") }
    val jobInfo by viewModel.info.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState("")

    val context = LocalContext.current

    // Leer el ID y los datos desde las preferencias
    val jobIdFromNotification = remember {
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            .getString("jobIdFromNotification", null)
    }

    val rawJson = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        .getString("jobDataFromNotification", null)

    println("🧾 JSON crudo desde prefs: $rawJson")

    val notificationExtras = remember {
        rawJson?.let { parseNotificationData(it) }
    }



    // Log para verificar datos
    LaunchedEffect(notificationExtras) {
        println(" Parsed NotificationData: $notificationExtras")
        println(" Número de teléfono: ${notificationExtras?.companyPhone}")
    }

    // Cargar información del trabajo si no se recibió por parámetro
    LaunchedEffect(jobIdFromNotification) {
        jobIdFromNotification?.toIntOrNull()?.let { jobId ->
            println("🔄 Cargando trabajo con ID desde notificación: $jobId")
            viewModel.fetchJobById(jobId.toString())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Información del Trabajo",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, selectedTab) { selectedTab = it }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                isLoading -> {
                    LoadingJobInformation()
                }

                error.isNotEmpty() -> {
                    ErrorJobInformation(error) {
                        val id = jobId ?: jobIdFromNotification
                        id?.let { viewModel.fetchJobById(it) }
                    }
                }

                jobInfo != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp)
                    ) {
                        jobInfo?.let { info ->
                            JobInformationItem(info, notificationExtras?.companyPhone)
                        }
                    }
                }

                else -> {
                    ErrorJobInformation("No se ha encontrado información del trabajo.") {
                        val id = jobId ?: jobIdFromNotification
                        id?.let { viewModel.fetchJobById(it) }
                    }
                }
            }
        }
    }
}

