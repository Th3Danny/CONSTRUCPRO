package com.example.myapplication.components.loginNavigate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.*


@Composable
fun ProfileDropdown(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = Modifier.wrapContentSize()) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Person, contentDescription = "Perfil")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Cerrar sesión") },
                onClick = {
                    logout(context)
                    navController.navigate("Login") {
                        popUpTo("Home") { inclusive = true }
                    }
                    expanded = false
                }
            )
        }
    }
}

private fun logout(context: Context) {
    val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    prefs.edit().clear().apply()
    Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
}