package com.example.myapplication.components.loginNavigate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun SwitchButtons(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.surfaceContainer
    val activeText = MaterialTheme.colorScheme.onPrimary
    val inactiveText = MaterialTheme.colorScheme.onPrimary

    // Contenedor principal con borde negro redondeado
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        color = inactiveColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Texto base para Inicio y Registro
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (selectedTab != "Inicio") onTabSelected("Inicio")
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // El texto no se muestra aquí pero el área es clickable
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (selectedTab != "Registro") onTabSelected("Registro")
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // El texto no se muestra aquí pero el área es clickable
                }
            }

            // Botón naranja activo
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f) // Exactamente la mitad del ancho
                    .fillMaxHeight()
                    .align(if (selectedTab == "Inicio") Alignment.CenterStart else Alignment.CenterEnd)
                    .clip(RoundedCornerShape(24.dp))
                    .background(activeColor)
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedTab,
                    fontWeight = FontWeight.Bold,
                    color = activeText
                )
            }

            // Texto del botón inactivo
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .align(if (selectedTab == "Inicio") Alignment.CenterEnd else Alignment.CenterStart)
                    .zIndex(2f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (selectedTab == "Inicio") "Registro" else "Inicio",
                    fontWeight = FontWeight.Bold,
                    color = inactiveText
                )
            }
        }
    }
}