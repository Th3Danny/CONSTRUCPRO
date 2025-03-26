package com.example.myapplication.components.loginNavigate


import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat

import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProfile(
    username: String?,
    imageUri: Uri?,
    context: Context,
    onLogoutClick: () -> Unit,
    onImagePick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text("CiviBridge", color = MaterialTheme.colorScheme.onPrimary)
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menú",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Foto
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri ?: R.drawable.ic_launcher_background),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                onImagePick()
                            }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nombre
                    Text(
                        text = username ?: "Usuario",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón rojo para cerrar sesión
                    Button(
                        onClick = {
                            expanded = false
                            onLogoutClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Cerrar sesión", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

