package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = viewModel(), // Se puede pasar un ViewModel predeterminado
    userId: String
) {
    val messages by chatViewModel.messages.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        chatViewModel.fetchMessages(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(messages) { message ->
                ChatMessageItem(message)
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: com.example.myapplication.home.data.model.Message) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "${message.sender}:", color = Color(0xFFFF9800)) // Color Naranja
        Text(text = message.content, color = Color.White)
    }
}
