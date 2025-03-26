package com.example.myapplication.profile.presentation

import CertificationsForm
import EducationForm
import ProfessionalProfileForm
import SkillsForm
import WorkExperienceForm
import android.R.attr.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileConfigScreen(navController: NavController ) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var currentSection by remember { mutableStateOf("") }

    // BottomSheetState + ModalBottomSheet
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            when (currentSection) {
                "Perfil Profesional" -> ProfessionalProfileForm()
                "Experiencia Laboral" -> WorkExperienceForm()
                "Habilidades" -> SkillsForm()
                "Educación" -> EducationForm()
                "Certificaciones" -> CertificationsForm()
            }
        }
    }

    // Pantalla principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileSectionCard("Perfil Profesional") {
            currentSection = "Perfil Profesional"
            showBottomSheet = true
        }
        ProfileSectionCard("Experiencia Laboral") {
            currentSection = "Experiencia Laboral"
            showBottomSheet = true
        }
        ProfileSectionCard("Habilidades") {
            currentSection = "Habilidades"
            showBottomSheet = true
        }
        ProfileSectionCard("Educación") {
            currentSection = "Educación"
            showBottomSheet = true
        }
        ProfileSectionCard("Certificaciones") {
            currentSection = "Certificaciones"
            showBottomSheet = true
        }
    }
}



@Composable
fun ProfileSectionCard(title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}


