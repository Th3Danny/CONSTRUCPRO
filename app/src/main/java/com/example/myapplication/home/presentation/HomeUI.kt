package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar

@Composable
fun HomeScreen(navController: NavController, postViewModel: PostViewModel = viewModel()) {
    val posts by postViewModel.posts.observeAsState(emptyList())
    var selectedTab by remember { mutableStateOf("Publicaciones") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Publicaciones",
            color = Color(0xFFFF9800),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            items(posts) { post ->
                PostCard(post)
            }
        }

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}

