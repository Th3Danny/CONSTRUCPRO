package com.valhallatech.civibridge.components.nav

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.valhallatech.civibridge.components.loginNavigate.TopAppBarProfile
import com.valhallatech.civibridge.login.presentation.LoginViewModel


@Composable
fun TopAppBarProfileComponent(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("MyAppPrefs", android.content.Context.MODE_PRIVATE)
    val username = prefs.getString("username", "Usuario")
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para seleccionar imagen desde galería
    val launcherGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
        }

    // Launcher para pedir permiso
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherGallery.launch("image/*") // Abrimos la galería
            }
        }

    TopAppBarProfile(
        navController = navController,
        username = username,
        imageUri = imageUri,
        context = context,
        onLogoutClick = {
            loginViewModel.logout(context)
            navController.navigate("Login") {
                popUpTo("Home") { inclusive = true }
            }
        },
        onImagePick = {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            launcherGallery.launch("image/*")
        } else {
            permissionLauncher.launch(permission)
        }
        }
    )
}
