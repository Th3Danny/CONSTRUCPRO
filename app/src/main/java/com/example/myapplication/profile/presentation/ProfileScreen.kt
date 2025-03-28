package com.example.myapplication.profile.presentation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.navigation.NavController
import com.example.myapplication.components.footer.BottomNavigationBar
import com.example.myapplication.components.nav.TopAppBarProfileComponent
import com.example.myapplication.login.presentation.LoginViewModel
import com.example.myapplication.profile.data.model.CompleteProfileData
import com.example.myapplication.profile.presentation.components.CertificationItem
import com.example.myapplication.profile.presentation.components.EducationItem
import com.example.myapplication.profile.presentation.components.EmptySection
import com.example.myapplication.profile.presentation.components.ExperienceItem
import com.example.myapplication.profile.presentation.components.ProfileHeader
import com.example.myapplication.profile.presentation.components.SectionHeader
import com.example.myapplication.profile.presentation.components.SkillChip

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = viewModel(), loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val profileState by viewModel.profileState
    var selectedTab by remember { mutableStateOf("Perfil") }

    // Cargar datos del perfil al iniciar la pantalla
    LaunchedEffect(Unit) {
        val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)
        viewModel.getCompleteProfile(userId)
    }

    Scaffold(
        topBar = {
            TopAppBarProfileComponent(
                navController = navController,
                loginViewModel = loginViewModel
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
                // Estado de carga
                profileState.isLoading -> {
                    LoadingState()
                }

                // Estado de error
                profileState.error != null -> {
                    ErrorState(
                        errorMessage = profileState.error!!,
                        onRetry = {
                            val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                            val userId = sharedPref.getInt("userId", -1)
                            viewModel.getCompleteProfile(userId)
                        }
                    )
                }

                // Estado con datos
                profileState.data != null -> {
                    val profileData = profileState.data!!
                    ProfileContent(profileData)
                }
            }
        }
    }
}

/**
 * Muestra un indicador de carga
 */
@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

/**
 * Muestra un mensaje de error con opción para reintentar
 */
@Composable
private fun ErrorState(errorMessage: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Error al cargar el perfil",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Reintentar")
                }
            }
        }
    }
}

/**
 * Muestra el contenido principal del perfil
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileContent(profileData: CompleteProfileData) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de Perfil Profesional
        item {
            ProfileHeader(
                name = profileData.profile.name,
                email = profileData.profile.email,
                headline = profileData.profile.headline
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Sección de Experiencia Laboral
        item {
            SectionHeader(
                title = "Experiencia Laboral",
                icon = Icons.Default.BusinessCenter
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (profileData.experiences.isEmpty()) {
            item {
                EmptySection("No hay experiencia laboral agregada")
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            items(profileData.experiences) { exp ->
                ExperienceItem(
                    title = exp.title,
                    company = exp.company,
                    location = exp.location,
                    description = exp.description
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Sección de Educación
        item {
            SectionHeader(
                title = "Educación",
                icon = Icons.Default.School
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (profileData.educations.isEmpty()) {
            item {
                EmptySection("No hay educación agregada")
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            items(profileData.educations) { edu ->
                EducationItem(
                    degree = edu.degree,
                    institution = edu.institution,
                    fieldOfStudy = edu.field_of_study,
                    description = edu.description
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Sección de Habilidades
        item {
            SectionHeader(
                title = "Habilidades",
                icon = Icons.Default.Star
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (profileData.skills.isEmpty()) {
            item {
                EmptySection("No hay habilidades agregadas")
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            item {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    profileData.skills.forEach { skill ->
                        SkillChip(
                            name = skill.name,
                            proficiency = skill.proficiency
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Sección de Certificaciones
        item {
            SectionHeader(
                title = "Certificaciones",
                icon = Icons.Default.Verified
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (profileData.certifications.isEmpty()) {
            item {
                EmptySection("No hay certificaciones agregadas")
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            items(profileData.certifications) { cert ->
                CertificationItem(
                    name = cert.name,
                    organization = cert.issuing_organization,
                    credentialId = cert.credential_id
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Espacio extra al final
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
