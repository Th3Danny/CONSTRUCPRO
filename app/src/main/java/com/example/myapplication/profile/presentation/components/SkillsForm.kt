

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.profile.data.model.SkillsRequest
import com.example.myapplication.profile.data.model.WorkExperienceRequest
import com.example.myapplication.profile.presentation.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsForm(
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val profileId = remember {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.getInt("idProfile", -1)
    }

    var skillName by remember { mutableStateOf("") }
    var proficiency by remember { mutableStateOf("3") } // Del 1 al 5
    var proficiencyLevel by remember { mutableStateOf(3) }

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Habilidades",
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
                .padding(16.dp)
        ) {
            Text(
                "Agregar Habilidad",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = skillName,
                onValueChange = { skillName = it },
                label = { Text("Nombre de la habilidad") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Nivel de dominio: $proficiencyLevel",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = proficiencyLevel.toFloat(),
                onValueChange = {
                    proficiencyLevel = it.toInt()
                    proficiency = it.toInt().toString()
                },
                valueRange = 1f..5f,
                steps = 3,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Principiante", style = MaterialTheme.typography.bodySmall)
                Text("Experto", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val skills = SkillsRequest(
                        profile_id = profileId,
                        name = skillName,
                        proficiency = proficiencyLevel
                    )
                    viewModel.submitSkills(skills)
                    Log.d("ProfileForm", "Guardando habilidad: $skillName, nivel: $proficiency")
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Guardar",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}