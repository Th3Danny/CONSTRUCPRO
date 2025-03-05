package com.example.myapplication.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.core.navigation.BottomNavigationBar
import com.example.myapplication.home.data.model.Post
@Composable
fun PostScreen(navController: NavController, postViewModel: PostViewModel) {
    val posts by postViewModel.posts.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedTab by remember { mutableStateOf("Post") }
        // 🔹 Logo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Construc",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Pro",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Tarjeta de Publicaciones
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Publicaciones",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(posts) { post ->
                        PostCard(post)
                    }
                }
            }
        }

        // 🔹 Barra de Navegación

        BottomNavigationBar(navController, selectedTab) { selectedTab = it }
    }
}


@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, color = Color(0xFFFF9800), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.description, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Autor: ${post.author}", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
        }
    }
}

