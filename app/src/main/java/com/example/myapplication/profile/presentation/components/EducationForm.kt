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
fun EducationForm(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val profileId = prefs.getInt("userId", -1)

    var institution by remember { mutableStateOf("") }
    var degree by remember { mutableStateOf("") }
    var fieldOfStudy by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Educación") },
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
            Text("Agregar Educación", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = institution,
                onValueChange = { institution = it },
                label = { Text("Institución") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = degree,
                onValueChange = { degree = it },
                label = { Text("Grado o título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fieldOfStudy,
                onValueChange = { fieldOfStudy = it },
                label = { Text("Campo de estudio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Fecha de inicio (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("Fecha de fin (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                Log.d("ProfileForm", "Enviando Skills Academicas")
                onDismiss()
            },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                Text("Guardar", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
