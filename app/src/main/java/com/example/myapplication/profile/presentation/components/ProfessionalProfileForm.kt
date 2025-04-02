

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.components.externalApplications.ProfilePicturePicker
import com.example.myapplication.profile.data.model.ProfessionalProfileRequest
import com.example.myapplication.profile.presentation.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalProfileForm(
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt("userId", -1)

    // Variables corregidas para cada campo individual
    var headline by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }
    val isSaved by viewModel.isProfileSaved

    LaunchedEffect(isSaved) {
        if (isSaved) {
            onDismiss()
            viewModel.isProfileSaved.value = false
        }
    }
//    // Usamos LaunchedEffect para cargar datos si existen
//    LaunchedEffect(Unit) {
//        viewModel.getProfileData()?.let { profile ->
//            headline = profile.headline ?: ""
//            about = profile.about ?: ""
//            location = profile.location ?: ""
//            contactPhone = profile.contactPhone ?: ""
//            profileImageUrl = profile.profileImageUrl ?: ""
//        }
//    }

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Justo antes del Spacer para headline
            ProfilePicturePicker { selectedUri ->
                profileImageUrl = selectedUri?.toString() ?: ""
            }

            Spacer(modifier = Modifier.height(16.dp))


            // Campo Titular
            OutlinedTextField(
                value = headline,
                onValueChange = { headline = it },
                label = { Text("Titular profesional") },
                placeholder = { Text("Ej: Desarrollador Android Senior") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Acerca de ti
            OutlinedTextField(
                value = about,
                onValueChange = { about = it },
                label = { Text("Acerca de ti") },
                placeholder = { Text("Describe tu experiencia y objetivos profesionales") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Ubicación
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicación") },
                placeholder = { Text("Ej: Madrid, España") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = "Ubicación",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Teléfono
            OutlinedTextField(
                value = contactPhone,
                onValueChange = { contactPhone = it },
                label = { Text("Teléfono de contacto") },
                placeholder = { Text("Ej: +34 612 345 678") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Teléfono",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo URL de foto
            OutlinedTextField(
                value = profileImageUrl,
                onValueChange = { profileImageUrl = it },
                label = { Text("URL de la foto de perfil") },
                placeholder = { Text("https://...") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón guardar
            Button(
                onClick = {
                    val profile = ProfessionalProfileRequest(
                        user_id = userId,
                        headline = headline,
                        about = about,
                        location = location,
                        contact_phone = contactPhone,
                        profile_imageUrl = profileImageUrl
                    )
                    viewModel.submitProfessionalProfile(profile)
                }
                ,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Guardar Perfil",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}