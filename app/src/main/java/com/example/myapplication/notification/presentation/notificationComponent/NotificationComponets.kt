package com.example.myapplication.notification.presentation.notificationComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.notification.data.model.Notification
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun NotificationItem(notification: Notification, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Icono de notificación
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notificación",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 4.dp, end = 16.dp)
                    .size(24.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                // Título
                Text(
                    text = notification.title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Cuerpo
                Text(
                    text = notification.body,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha
                Text(
                    text = formatDate(notification.sent_at),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Light
                )
            }

            // Indicador de leído (si está disponible)
//            if (notification.red == true) {
//                Icon(
//                    imageVector = Icons.Default.Check,
//                    contentDescription = "Leído",
//                    tint = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier
//                        .padding(start = 8.dp)
//                        .size(16.dp)
//                )
//            }
        }
    }
}

/**
 * Muestra un mensaje cuando no hay notificaciones
 */
@Composable
fun EmptyNotifications() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Sin notificaciones",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "No tienes notificaciones",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Formatea la fecha de la notificación de manera más amigable
 */
private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = inputFormat.parse(dateString) ?: return dateString

        val calendar = Calendar.getInstance()
        val today = Calendar.getInstance()
        calendar.time = date

        val outputFormat = when {
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) -> {
                // Si es hoy, muestra "Hoy, HH:MM"
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                "Hoy, ${timeFormat.format(date)}"
            }
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 1 -> {
                // Si fue ayer, muestra "Ayer, HH:MM"
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                "Ayer, ${timeFormat.format(date)}"
            }
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) -> {
                // Si es este año, muestra "dd MMM, HH:MM"
                SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault())
            }
            else -> {
                // Si es otro año, muestra "dd MMM yyyy, HH:MM"
                SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            }
        }

        if (outputFormat is SimpleDateFormat) {
            outputFormat.format(date)
        } else {
            outputFormat.toString()
        }
    } catch (e: Exception) {
        // Si hay un error en el formato, devuelve la fecha original
        dateString
    }
}