package com.example.myapplication.job.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.components.nav.TopAppBarProfileComponent
import com.example.myapplication.components.footer.BottomNavigationBar
import com.example.myapplication.job.data.model.JobApplication
import com.example.myapplication.login.presentation.LoginViewModel
import com.example.myapplication.ui.theme.ErrorColor
import com.example.myapplication.ui.theme.SuccessColor
import com.example.myapplication.ui.theme.WarningColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobScreen(navController: NavController, jobViewModel: JobViewModel, loginViewModel: LoginViewModel) {
    val jobs by jobViewModel.jobs.observeAsState(emptyList())
    val pendingJobs by jobViewModel.pendingJobs.observeAsState(emptyList())
    val acceptedJobs by jobViewModel.acceptedJobs.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Ofertas") }

    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE) }
    val applicantId = remember { sharedPreferences.getInt("userId", -1) }

    val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val savedImageUri = prefs.getString("profileImageUri", null)
    var imageUri by remember { mutableStateOf<Uri?>(savedImageUri?.let { Uri.parse(it) }) }

    val launcherGallery = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            // Guardar en SharedPreferences
            val editor = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE).edit()
            editor.putString("profileImageUri", it.toString())
            editor.apply()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launcherGallery.launch("image/*")
        } else {
            Toast.makeText(context, "Permiso denegado para acceder a la galería", Toast.LENGTH_SHORT).show()
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
            TabButton("Aceptados", selectedTab) { selectedTab = "Aceptados" }
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
        "Aceptado" to SuccessColor,
        "Rechazado" to ErrorColor
    )

    val statusBgColors = mapOf(
        "Pendiente" to WarningColor.copy(alpha = 0.1f),
        "Aceptado" to SuccessColor.copy(alpha = 0.1f),
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
                    imageVector = Icons.Default.DateRange, // Cambia esto si usas un icono personalizado
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
