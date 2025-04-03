package com.example.myapplication.jobInformation.presentation.componets

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.jobInformation.data.model.InformationJobRequest
@Composable
fun JobInformationItem(info: InformationJobRequest, companyPhoneFromNotification: String? = null) {
    val context = LocalContext.current
    val rawPhone = companyPhoneFromNotification ?: info.companyPhone ?: ""

    val cleanedPhone = rawPhone.filter { it.isDigit() }
    val phoneForWhatsApp = "52$cleanedPhone"
    val phoneForDial = cleanedPhone

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = info.title ?: "Sin título",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Descripción",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = info.description ?: "Sin descripción",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))


            JobDetailRow(
                icon = Icons.Default.LocationOn,
                label = "Ubicación",
                value = info.location ?: "No disponible"
            )

            JobDetailRow(
                icon = Icons.Default.MonetizationOn,
                label = "Salario",
                value = "$${info.salary ?: 0.0}",
                highlight = true
            )

            JobDetailRow(
                icon = Icons.Default.People,
                label = "Aplicaciones",
                value = "${info.application_count ?: 0}"
            )

            JobDetailRow(
                icon = Icons.Default.Favorite,
                label = "Likes",
                value = "${info.like_count ?: 0}"
            )



            JobDetailRow(
                icon = Icons.Default.Phone,
                label = "Contacto",
                value = if (cleanedPhone.isEmpty()) "No disponible" else cleanedPhone,
                onClick = {
                    if (cleanedPhone.isNotEmpty()) {
                        // Intent para marcar
                        val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneForDial"))

                        // Intent para WhatsApp con mensaje
                        val message = "Hola, estoy interesado en la vacante de ${info.title ?: "tu empresa"}"
                        val uriWhatsApp = Uri.parse("https://api.whatsapp.com/send?phone=$phoneForWhatsApp&text=${Uri.encode(message)}")
                        val intentWhatsApp = Intent(Intent.ACTION_VIEW, uriWhatsApp)

                        val pm = context.packageManager
                        val canOpenWhatsApp = intentWhatsApp.resolveActivity(pm) != null

                        val chooser = Intent.createChooser(intentDial, "Selecciona una app")

                        if (canOpenWhatsApp) {
                            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intentWhatsApp))
                        } else {
                            Toast.makeText(context, "WhatsApp no está disponible", Toast.LENGTH_SHORT).show()
                        }

                        context.startActivity(chooser)
                    }
                }
            )




            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { /* Guardar */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = "Guardar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { /* Aplicar */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Aplicar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Aplicar")
                }
            }
        }
    }
}



@Composable
fun JobDetailRow(
    icon: ImageVector,
    label: String,
    value: String,
    highlight: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val rowModifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    } else {
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    }

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = value,
                color = if (highlight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}


@Composable
fun LoadingJobInformation() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Cargando información del trabajo...",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ErrorJobInformation(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Error al cargar la información",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Reintentar")
                }
            }
        }
    }
}