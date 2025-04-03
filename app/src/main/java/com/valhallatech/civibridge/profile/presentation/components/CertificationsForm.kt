

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.valhallatech.civibridge.profile.data.model.CertificationRequest
import com.valhallatech.civibridge.profile.presentation.ProfileViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CertificationsForm(viewModel: ProfileViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val profileId = remember {
        val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        prefs.getInt("idProfile", -1)
    }


    var name by remember { mutableStateOf("") }
    var issuingOrg by remember { mutableStateOf("") }
    var issueDate by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var credentialId by remember { mutableStateOf("") }
    var credentialUrl by remember { mutableStateOf("") }
    var noExpiration by remember { mutableStateOf(false) }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "Certificaciones",
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
                "Agregar Certificación",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la certificación") },
                placeholder = { Text("Ej: AWS Certified Solutions Architect") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = issuingOrg,
                onValueChange = { issuingOrg = it },
                label = { Text("Organización emisora") },
                placeholder = { Text("Ej: Amazon Web Services") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = issueDate,
                onValueChange = { issueDate = it },
                label = { Text("Fecha de emisión") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = "Fecha",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                },
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
                    checked = noExpiration,
                    onCheckedChange = {
                        noExpiration = it
                        if (it) expirationDate = ""
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    "No caduca",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            AnimatedVisibility(visible = !noExpiration) {
                OutlinedTextField(
                    value = expirationDate,
                    onValueChange = { expirationDate = it },
                    label = { Text("Fecha de expiración") },
                    placeholder = { Text("YYYY-MM-DD") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !noExpiration,
                    leadingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Fecha",
                            tint = if (noExpiration)
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            else
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        )
                    },
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
                value = credentialId,
                onValueChange = { credentialId = it },
                label = { Text("ID de la credencial") },
                placeholder = { Text("Ej: ABC123XYZ") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = credentialUrl,
                onValueChange = { credentialUrl = it },
                label = { Text("URL de la credencial") },
                placeholder = { Text("https://...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Link,
                        contentDescription = "URL",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (issueDate.isNotBlank()) {
                        try {
                            val parsedIssueDate = LocalDate.parse(issueDate, formatter)
                            val parsedExpirationDate = if (!noExpiration && expirationDate.isNotBlank()) {
                                LocalDate.parse(expirationDate, formatter)
                            } else null

                            val certification = CertificationRequest(
                                profile_id = profileId,
                                name = name,
                                issuing_organization = issuingOrg,
                                issue_date = parsedIssueDate,
                                expiration_date = parsedExpirationDate,
                                credential_id = credentialId,
                                credential_url = credentialUrl
                            )

                            viewModel.submitCertification(certification)
                            Log.d("ProfileForm", "Guardando certificación: $name")
                            onDismiss()
                        } catch (e: DateTimeParseException) {
                            Toast.makeText(context, "Formato de fecha inválido. Usa yyyy-MM-dd", Toast.LENGTH_LONG).show()
                            Log.e("CertificationsForm", "Error al parsear fecha: ${e.message}")
                        }
                    } else {
                        Toast.makeText(context, "La fecha de emisión es obligatoria", Toast.LENGTH_SHORT).show()
                    }
                }
                ,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Guardar Certificación",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}