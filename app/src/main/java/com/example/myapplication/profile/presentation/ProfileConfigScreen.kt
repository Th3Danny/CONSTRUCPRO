package com.example.myapplication.profile.presentation



import CertificationsForm
import EducationForm
import ProfessionalProfileForm
import SkillsForm
import WorkExperienceForm
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileConfigScreen(navController: NavController) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var currentSection by remember { mutableStateOf("") }

    // BottomSheetState + ModalBottomSheet
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            when (currentSection) {
                "Perfil Profesional" -> ProfessionalProfileForm(onDismiss = { showBottomSheet = false })
                "Experiencia Laboral" -> WorkExperienceForm(onDismiss = { showBottomSheet = false })
                "Habilidades" -> SkillsForm(onDismiss = { showBottomSheet = false })
                "Educación" -> EducationForm(onDismiss = { showBottomSheet = false })
                "Certificaciones" -> CertificationsForm(onDismiss = { showBottomSheet = false })
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Configuración de Perfil",
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
        }
    ) { padding ->
        // Pantalla principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ProfileSectionCard("Perfil Profesional", "Añade tu titular e información general") {
                currentSection = "Perfil Profesional"
                showBottomSheet = true
            }

            ProfileSectionCard("Experiencia Laboral", "Añade tus experiencias profesionales") {
                currentSection = "Experiencia Laboral"
                showBottomSheet = true
            }

            ProfileSectionCard("Habilidades", "Añade tus competencias y niveles") {
                currentSection = "Habilidades"
                showBottomSheet = true
            }

            ProfileSectionCard("Educación", "Añade tu formación académica") {
                currentSection = "Educación"
                showBottomSheet = true
            }

            ProfileSectionCard("Certificaciones", "Añade tus títulos y credenciales") {
                currentSection = "Certificaciones"
                showBottomSheet = true
            }
        }
    }
}

@Composable
fun ProfileSectionCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Ir a $title",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}