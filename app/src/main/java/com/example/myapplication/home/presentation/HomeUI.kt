package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    postViewModel: PostViewModel,
    navController: NavController,
    onNavigateToChat: () -> Unit,
    onNavigateToProjects: () -> Unit
) {
    NavigationBar(
        containerColor = Color.Black,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Publicaciones",
                    modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                )
            },
            label = { Text("Publicaciones", color = Color.White, modifier = Modifier.padding(top = 0.dp)) },
            selected = selectedTab == "Publicaciones",
            onClick = { onTabSelected("Publicaciones") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.MailOutline,
                    contentDescription = "Chat",
                    modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                )
            },
            label = { Text("Chat", color = Color.White, modifier = Modifier.padding(top = 0.dp)) },
            selected = selectedTab == "Chat",
            onClick = { onTabSelected("Chat") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = "Proyectos",
                    modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                )
            },
            label = { Text("Proyectos", color = Color.White, modifier = Modifier.padding(top = 0.dp)) },
            selected = selectedTab == "Proyectos",
            onClick = { onTabSelected("Proyectos") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Notifications,
                    contentDescription = "Notificaciones",
                    modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)
                )
            },
            label = { Text("Notificaciones", color = Color.White, modifier = Modifier.padding(top = 0.dp)) },
            selected = selectedTab == "Notificaciones",
            onClick = { onTabSelected("Notificaciones") }
        )
    }
}
