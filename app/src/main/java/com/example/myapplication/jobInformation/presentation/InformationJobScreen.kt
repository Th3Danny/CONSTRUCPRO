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
import com.example.myapplication.jobInformation.presentation.componets.ErrorJobInformation
import com.example.myapplication.jobInformation.presentation.componets.JobInformationItem
import com.example.myapplication.jobInformation.presentation.componets.LoadingJobInformation

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

    // Obtener el ID del trabajo desde las preferencias si no viene como parámetro
    val context = LocalContext.current
    val jobIdFromNotification = remember {
        context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            .getString("jobIdFromNotification", null)
    }

    // Cargar información del trabajo
    LaunchedEffect(jobId, jobIdFromNotification) {
        val id = jobId ?: jobIdFromNotification
        id?.let {
            viewModel.fetchInformation(it)

            // Limpiar el ID guardado en preferencias una vez usado
            context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE).edit()
                .remove("jobIdFromNotification")
                .apply()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Información del Trabajo",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
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
                        id?.let { viewModel.fetchInformation(it) }
                    }
                }

                jobInfo != null -> {
                    // Mostrar la información del trabajo
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp)
                    ) {
                        jobInfo?.let { info ->
                            JobInformationItem(info)
                        }
                    }
                }

                else -> {
                    // Estado inicial sin datos
                    ErrorJobInformation("No se ha encontrado información del trabajo.") {
                        val id = jobId ?: jobIdFromNotification
                        id?.let { viewModel.fetchInformation(it) }
                    }
                }
            }
        }
    }
}

