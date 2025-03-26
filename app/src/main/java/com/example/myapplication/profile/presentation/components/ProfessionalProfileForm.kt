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
fun ProfessionalProfileForm(
    onDismiss: () -> Unit // para ocultar el modal
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getInt("userId", -1)

    var headline by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil Profesional") },
//                navigationIcon = {
//                    IconButton(onClick = {}) {
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

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = headline, onValueChange = { headline = it }, label = { Text("Titular") })
            OutlinedTextField(value = about, onValueChange = { about = it }, label = { Text("Acerca de ti") })
            OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Ubicación") })
            OutlinedTextField(value = contactPhone, onValueChange = { contactPhone = it }, label = { Text("Teléfono") })
            OutlinedTextField(value = profileImageUrl, onValueChange = { profileImageUrl = it }, label = { Text("URL de la foto") })

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                Log.d("ProfileForm", "Enviando perfil: userId=$userId, headline=$headline")
                onDismiss()
            }) {
                Text("Guardar", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
