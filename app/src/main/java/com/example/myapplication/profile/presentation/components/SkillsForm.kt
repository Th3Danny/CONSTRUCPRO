import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsForm(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val profileId = prefs.getInt("userId", -1)

    var skillName by remember { mutableStateOf("") }
    var proficiency by remember { mutableStateOf("1") } // del 1 al 5
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habilidades") },
//                navigationIcon = {
//                    IconButton(onClick = {  }) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
//                    }
//                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Agregar Habilidad", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = skillName,
                onValueChange = { skillName = it },
                label = { Text("Nombre de la habilidad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = proficiency,
                onValueChange = {
                    if (it.toIntOrNull() in 1..5 || it.isEmpty()) proficiency = it
                },
                label = { Text("Dominio (1 a 5)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                Log.d("ProfileForm",  "Habilidades")
                onDismiss()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                )

            {
                Text("Guardar", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
