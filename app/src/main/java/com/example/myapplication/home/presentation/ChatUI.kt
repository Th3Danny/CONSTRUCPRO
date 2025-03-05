package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar

// Modelo de datos falsos con `content`
data class Message(val id: String, val sender: String, val content: String)

@Composable
fun ChatScreen(navController: NavController) {
    val fakeMessages = listOf(
        Message("1", "Juan Pérez", "Hola, ¿cómo va el proyecto?"),
        Message("2", "María López", "Necesitamos una reunión para revisar avances."),
        Message("3", "Carlos Méndez", "Listo el informe de construcción.")
    )

    var selectedTab by remember { mutableStateOf("Chat") }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f).padding(16.dp)) {
            items(fakeMessages) { message ->
                ChatMessageItem(message)
            }
        }
        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

@Composable
fun ChatMessageItem(message: Message) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "${message.sender}:", color = Color(0xFFFF9800)) // Color Naranja
        Text(text = message.content, color = Color.White)
    }
}

