package com.valhallatech.civibridge.profile.presentation

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
fun ProfileConfigScreen(navController: NavController, viewModel: ProfileViewModel) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var currentSection by remember { mutableStateOf("") }

    // BottomSheetState con skipPartiallyExpanded para evitar estados intermedios
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            dragHandle = {
                // Drag handle personalizado más visible
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                                RoundedCornerShape(2.dp)
                            )
                    )
                }
            },
            // Fijamos una altura máxima para que no ocupe toda la pantalla
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            // Encabezado fijo para el formulario
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = currentSection,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
            }

            // Contenido del formulario
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 450.dp) // Altura mínima para que no quede muy pequeño
                    .padding(bottom = 16.dp) // Padding inferior para evitar que el teclado lo tape todo
            ) {
                // Aquí va el contenido del formulario
                when (currentSection) {
                    "Perfil Profesional" -> ProfessionalProfileForm(viewModel, onDismiss = { showBottomSheet = false })
                    "Experiencia Laboral" -> WorkExperienceForm(viewModel, onDismiss = { showBottomSheet = false })
                    "Habilidades" -> SkillsForm(viewModel, onDismiss = { showBottomSheet = false })
                    "Educación" -> EducationForm(viewModel, onDismiss = { showBottomSheet = false })
                    "Certificaciones" -> CertificationsForm(viewModel, onDismiss = { showBottomSheet = false })
                }
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