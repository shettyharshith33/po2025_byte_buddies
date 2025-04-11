package com.shettyharshith33.firebaseAuth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.shettyharshith33.beforeLoginScreens.BeforeLoginScreensNavigation
import com.shettyharshith33.vcputtur.ui.theme.themeBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.White.toArgb(),Color.White.toArgb()))
        setContent {
            val navController = rememberNavController()
            BeforeLoginScreensNavigation(navController)
        }
    }
}