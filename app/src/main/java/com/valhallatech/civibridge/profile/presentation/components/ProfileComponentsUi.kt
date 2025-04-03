package com.valhallatech.civibridge.profile.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Muestra el encabezado del perfil con avatar, nombre, email y titular
 */
@Composable
fun ProfileHeader(name: String, email: String, headline: String?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Surface(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50)),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .padding(20.dp)
                        .size(60.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Email
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Titular
            if (!headline.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = headline,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/**
 * Encabezado de sección con icono y título
 */
@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
        )
    }
}

/**
 * Elemento de experiencia laboral
 */
@Composable
fun ExperienceItem(title: String, company: String, location: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = company,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/**
 * Elemento de educación
 */
@Composable
fun EducationItem(degree: String, institution: String, fieldOfStudy: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = degree,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = institution,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Campo: $fieldOfStudy",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

/**
 * Contenedor para elementos en flujo (Flow)
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val maxWidth = constraints.maxWidth

        var yPos = 0
        var xPos = 0
        var maxHeight = 0

        val rowHeights = mutableListOf<Int>()
        val rowItems = mutableListOf<MutableList<Pair<Int, Int>>>()

        var currentRow = mutableListOf<Pair<Int, Int>>()

        placeables.forEach { placeable ->
            if (xPos + placeable.width > maxWidth) {
                // Nueva fila
                rowHeights.add(maxHeight)
                rowItems.add(currentRow)

                currentRow = mutableListOf()
                xPos = 0
                yPos += maxHeight
                maxHeight = 0
            }

            currentRow.add(Pair(xPos, placeable.height))
            xPos += placeable.width
            maxHeight = maxOf(maxHeight, placeable.height)
        }

        // Agregar la última fila
        if (currentRow.isNotEmpty()) {
            rowHeights.add(maxHeight)
            rowItems.add(currentRow)
        }

        val height = rowHeights.sum()

        layout(maxWidth, height) {
            var y = 0
            rowItems.forEachIndexed { rowIndex, row ->
                val rowHeight = rowHeights[rowIndex]
                row.forEachIndexed { index, (x, _) ->
                    placeables[rowItems.take(rowIndex).sumOf { it.size } + index].placeRelative(
                        x = x,
                        y = y + (rowHeight - placeables[rowItems.take(rowIndex).sumOf { it.size } + index].height) / 2
                    )
                }
                y += rowHeight
            }
        }
    }
}

/**
 * Chip para mostrar una habilidad con su nivel
 */
@Composable
fun SkillChip(name: String, proficiency: Int) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = when (proficiency) {
            in 4..5 -> MaterialTheme.colorScheme.primaryContainer
            in 2..3 -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
            else -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        },
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(4.dp))

            // Mostrar nivel de habilidad con estrellas
            Row {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = if (index < proficiency)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

/**
 * Elemento de certificación
 */
@Composable
fun CertificationItem(name: String, organization: String, credentialId: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = organization,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            if (credentialId.isNotEmpty()) {
                Text(
                    text = "ID: $credentialId",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Mensaje para secciones sin datos
 */
@Composable
fun EmptySection(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier.padding(16.dp)
        )
    }
}