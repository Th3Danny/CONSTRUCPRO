package com.valhallatech.civibridge.job.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.valhallatech.civibridge.components.nav.TopAppBarProfileComponent
import com.valhallatech.civibridge.components.footer.BottomNavigationBar
import com.valhallatech.civibridge.job.data.model.JobApplication
import com.valhallatech.civibridge.login.presentation.LoginViewModel
import com.valhallatech.civibridge.ui.theme.ErrorColor
import com.valhallatech.civibridge.ui.theme.WarningColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobScreen(navController: NavController, jobViewModel: JobViewModel, loginViewModel: LoginViewModel) {
    val jobs by jobViewModel.jobs.collectAsState()
    val pendingJobs by jobViewModel.pendingJobs.collectAsState()
    val acceptedJobs by jobViewModel.acceptedJobs.collectAsState()

    var selectedTab by remember { mutableStateOf("Ofertas") }

    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE) }
    val applicantId = remember { sharedPreferences.getInt("userId", -1) }

    val jobUpdateReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val type = intent?.getStringExtra("job_update_type")
                Log.d("📡 JobUpdateReceiver", "📥 Recibido tipo: $type")

                when (type) {
                    "NEW_JOB" -> jobViewModel.refreshJobs()
                    "JOB_APPLIED" -> jobViewModel.refreshPendingJobs()
                    "JOB_ACCEPTED" -> jobViewModel.refreshAcceptedJobs()
                    "JOB_Apply" -> jobViewModel.refreshApplyJobs()
                    "REFRESH_ALL" -> {
                        jobViewModel.refreshJobs()
                        jobViewModel.refreshPendingJobs()
                        jobViewModel.refreshAcceptedJobs()
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        val intentFilter = IntentFilter("com.example.UPDATE_JOB_DATA")
        context.registerReceiver(jobUpdateReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)

        onDispose {
            context.unregisterReceiver(jobUpdateReceiver)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBarProfileComponent(
            navController = navController,
            loginViewModel = loginViewModel
        )


        Text(
            text = "Ofertas de Trabajo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de pestañas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TabButton("Ofertas", selectedTab) { selectedTab = "Ofertas" }
            TabButton("Pendientes", selectedTab) { selectedTab = "Pendientes" }
        }

        Spacer(modifier = Modifier.height(12.dp))

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
                                jobViewModel.applyToJob(jobId, applicantId)
                            } else {
                                Log.e("JobScreen", "Usuario no autenticado")
                            }
                        }
                    }
                }
                "Pendientes" -> {
                    items(pendingJobs) { jobApplication ->
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
            containerColor = if (selectedTab == text)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.padding(horizontal = 4.dp),
        shape = RoundedCornerShape(50.dp), // Más redondeado para el diseño deseado
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (selectedTab == text)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.primary ?: Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun JobApplicationItem(jobApplication: JobApplication) {
    val statusColors = mapOf(
        "Pendiente" to WarningColor,
        "Rechazado" to ErrorColor
    )

    val statusBgColors = mapOf(
        "Pendiente" to WarningColor.copy(alpha = 0.1f),
        "Rechazado" to ErrorColor.copy(alpha = 0.1f)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = jobApplication.job_title,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Indicador de estado con colores semánticos
            Surface(
                color = statusBgColors[jobApplication.status] ?: MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "Estado: ${jobApplication.status}",
                    color = statusColors[jobApplication.status] ?: MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Fecha",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Aplicado el: ${jobApplication.applied_at}",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
            }
        }
    }
}
