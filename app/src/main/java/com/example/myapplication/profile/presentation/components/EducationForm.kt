

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.profile.data.model.EducationRequest
import com.example.myapplication.profile.data.model.WorkExperienceRequest
import com.example.myapplication.profile.presentation.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationForm(viewModel: ProfileViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val profileId = remember {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.getInt("idProfile", -1)
    }

    var institution by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var fieldOfStudy by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var inProgress by remember { mutableStateOf(false) }
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Educación",
//                        style = MaterialTheme.typography.titleMedium.copy(
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                "Agregar Educación",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = institution,
                onValueChange = { institution = it },
                label = { Text("Institución") },
                placeholder = { Text("Ej: Universidad Complutense de Madrid") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = degree,
                onValueChange = { degree = it },
                label = { Text("Grado o título") },
                placeholder = { Text("Ej: Licenciatura, Máster, etc.") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = fieldOfStudy,
                onValueChange = { fieldOfStudy = it },
                label = { Text("Campo de estudio") },
                placeholder = { Text("Ej: Informática, Medicina, etc.") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Fecha de inicio") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = inProgress,
                    onCheckedChange = {
                        inProgress = it
                        if (it) endDate = ""
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    "En curso actualmente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            AnimatedVisibility(visible = !inProgress) {
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("Fecha de fin") },
                    placeholder = { Text("YYYY-MM-DD") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !inProgress,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = { Text("Describe tu experiencia educativa...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (startDate.isNotBlank()) {
                        try {
                            val parsedStartDate = LocalDate.parse(startDate, formatter)
                            val parsedEndDate = if (!inProgress && endDate.isNotBlank()) {
                                LocalDate.parse(endDate, formatter)
                            } else null

                            val education = EducationRequest(
                                profile_id = profileId,
                                institution = institution,
                                degree = degree,
                                field_of_study = fieldOfStudy,
                                start_date = parsedStartDate,
                                end_date = parsedEndDate,
                                description = description
                            )

                            viewModel.submitEducation(education)
                            Log.d("ProfileForm", "Guardando educación: $degree en $institution")
                            onDismiss()
                        } catch (e: DateTimeParseException) {
                            Toast.makeText(context, "Formato de fecha inválido. Usa yyyy-MM-dd", Toast.LENGTH_LONG).show()
                            Log.e("EducationForm", "Error al parsear fecha: ${e.message}")
                        }
                    } else {
                        Toast.makeText(context, "La fecha de inicio es obligatoria", Toast.LENGTH_SHORT).show()
                    }
                }
                ,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Guardar Educación",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}