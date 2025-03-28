

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.profile.data.model.WorkExperienceRequest
import com.example.myapplication.profile.presentation.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkExperienceForm(
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val profileId = remember {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.getInt("idProfile", -1)
    }


    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var isCurrent by remember { mutableStateOf(false) }

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Experiencia Laboral",
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
//        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                "Agregar Experiencia Laboral",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título del puesto") },
                placeholder = { Text("Ej: Desarrollador Senior") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = company,
                onValueChange = { company = it },
                label = { Text("Empresa") },
                placeholder = { Text("Ej: Google") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicación") },
                placeholder = { Text("Ej: Madrid, España") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = { Text("Describe tus responsabilidades y logros...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isCurrent,
                    onCheckedChange = {
                        isCurrent = it
                        if (it) endDate = ""
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    "Trabajo actual",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            AnimatedVisibility(visible = !isCurrent) {
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("Fecha de fin") },
                    placeholder = { Text("YYYY-MM-DD") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isCurrent,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
                        disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            Button(
                onClick = {
                    if (startDate.isNotBlank()) {
                        try {
                            val parsedStartDate = LocalDate.parse(startDate, formatter)
                            val parsedEndDate = if (!isCurrent && endDate.isNotBlank()) {
                                LocalDate.parse(endDate, formatter)
                            } else null

                            val workExperience = WorkExperienceRequest(
                                profile_id = profileId,
                                title = title,
                                company = company,
                                location = location,
                                description = description,
                                is_current = isCurrent,
                                start_date = parsedStartDate,
                                end_date = if (isCurrent) null else LocalDate.parse(endDate)

                            )

                            viewModel.submitWorkExperience(workExperience)
                            Log.d("ProfileForm", "Guardando experiencia: $title en $company")
                            onDismiss()

                        } catch (e: DateTimeParseException) {
                            Toast.makeText(context, "Formato de fecha inválido. Usa yyyy-MM-dd", Toast.LENGTH_LONG).show()
                            Log.e("WorkExperienceForm", " Error al parsear fecha: ${e.message}")
                        }
                    } else {
                        Toast.makeText(context, "La fecha de inicio es obligatoria", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Guardar Experiencia", color = MaterialTheme.colorScheme.onPrimary)
            }


        }
    }
}