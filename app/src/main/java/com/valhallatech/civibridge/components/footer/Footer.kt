package com.valhallatech.civibridge.components.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(navController: NavController, selectedTab: String, onTabSelected: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Filled.AddCircle,
                label = "Publicaciones",
                isSelected = selectedTab == "Publicaciones",
                onClick = {
                    onTabSelected("Publicaciones")
                    navController.navigate("Home")
                }
            )

            BottomNavItem(
                icon = Icons.Filled.Person,
                label = "Perfil",
                isSelected = selectedTab == "Perfil",
                onClick = {
                    onTabSelected("Perfil")
                    navController.navigate("ProfileScreen")

                }
            )

            BottomNavItem(
                icon = Icons.Filled.Info,
                label = "Proyectos",
                isSelected = selectedTab == "Proyectos",
                onClick = {
                    onTabSelected("Proyectos")
                    navController.navigate("Projects")
                }
            )

            BottomNavItem(
                icon = Icons.Filled.Notifications,
                label = "Notificaciones",
                isSelected = selectedTab == "Notificaciones",
                onClick = {
                    onTabSelected("Notificaciones")
                    navController.navigate("Notifications")
                }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )

        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}
