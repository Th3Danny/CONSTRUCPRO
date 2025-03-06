package com.example.myapplication.core.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController, selectedTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = Color.Black,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AddCircle, contentDescription = "Jobs", modifier = Modifier.size(24.dp)) },
            label = { Text("Publicaciones", color = Color.White) },
            selected = selectedTab == "Publicaciones",
            onClick = { onTabSelected("Publicaciones"); navController.navigate("Home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.MailOutline, contentDescription = "Chat", modifier = Modifier.size(24.dp)) },
            label = { Text("Chat", color = Color.White) },
            selected = selectedTab == "Chat",
            onClick = { onTabSelected("Chat"); navController.navigate("Chat") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "Proyectos", modifier = Modifier.size(24.dp)) },
            label = { Text("Proyectos", color = Color.White) },
            selected = selectedTab == "Proyectos",
            onClick = { onTabSelected("Proyectos"); navController.navigate("Projects") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones", modifier = Modifier.size(24.dp)) },
            label = { Text("Notificaciones", color = Color.White) },
            selected = selectedTab == "Notificaciones",
            onClick = { onTabSelected("Notificaciones"); navController.navigate("Notifications") }
        )
    }
}
