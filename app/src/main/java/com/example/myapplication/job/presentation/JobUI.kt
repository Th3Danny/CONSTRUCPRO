package com.example.myapplication.job.presentation

import android.R.attr.permission
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.myapplication.components.loginNavigate.TopAppBarProfile
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.job.data.model.JobApplication
import com.example.myapplication.login.presentation.LoginViewModel



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
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarProfile(
            username = prefs.getString("username", "Usuario"),
            imageUri = imageUri,
            context = context,
            onLogoutClick = {
                loginViewModel.logout(context)
                navController.navigate("Login") {
                    popUpTo("Home") { inclusive = true }
                }
            },
            onImagePick = {
                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    android.Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                }

                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    launcherGallery.launch("image/*")
                } else {
                    permissionLauncher.launch(permission)
                }
            }
        )


        Text(
            text = "Ofertas de Trabajo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF9800),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //  Selector de pestañas
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
                                jobViewModel.applyToJob(jobId, applicantId)
                            } else {
                                Log.e("JobScreen", " Usuario no autenticado")
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






