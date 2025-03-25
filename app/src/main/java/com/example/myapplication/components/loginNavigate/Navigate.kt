package com.example.myapplication.components.loginNavigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun SwitchButtons(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.background
    val activeText = MaterialTheme.colorScheme.onBackground
    val inactiveText = MaterialTheme.colorScheme.surface

    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        // Fondo redondeado base

        Row(
            modifier = Modifier
                .matchParentSize()
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .zIndex(if (selectedTab == "Inicio") 1f else 0f)
            ) {
                Button(
                    onClick = { onTabSelected("Inicio") },
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = if (selectedTab == "Inicio") 35.dp else 0.dp), // Superposición sutil
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == "Inicio") activeColor else inactiveColor,
                        contentColor = if (selectedTab == "Inicio") activeText else inactiveText
                    ),
                    elevation = null
                ) {
                    Text("Inicio", fontWeight = FontWeight.Bold)
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .zIndex(if (selectedTab == "Registro") 1f else 0f)
            ) {
                Button(
                    onClick = { onTabSelected("Registro") },
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = if (selectedTab == "Registro") (-35).dp else 0.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == "Registro") activeColor else inactiveColor,
                        contentColor = if (selectedTab == "Registro") activeText else inactiveText
                    ),
                    elevation = null
                ) {
                    Text("Registro", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}