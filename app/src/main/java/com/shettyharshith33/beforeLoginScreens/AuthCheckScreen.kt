package com.shettyharshith33.beforeLoginScreens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthCheckScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val isUserLoggedIn = auth.currentUser != null

    LaunchedEffect(Unit){
        if (isUserLoggedIn) {
            navController.navigate(BeforeLoginScreensNavigationObject.HOME_SCREEN) {
                popUpTo(0) // Clear backstack
            }
        } else {
            navController.navigate(BeforeLoginScreensNavigationObject.ONBOARDING_SCREEN) {
                popUpTo(0)
            }
        }
    }
}

