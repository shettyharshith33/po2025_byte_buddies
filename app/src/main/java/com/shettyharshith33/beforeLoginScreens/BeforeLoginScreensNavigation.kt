package com.shettyharshith33.beforeLoginScreens

import EvaluationScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yourpackage.ui.screens.ResultScreen


object BeforeLoginScreensNavigationObject {
    const val AUTH_CHECK = "authCheck"
    const val EVALUATION_SCREEN = "evaluationScreen"
    const val ONBOARDING_SCREEN = "onBoardingScreen"
    const val SIGNUP_SCREEN = "signUpScreen"
    const val LOGIN_SCREEN = "loginScreen"
    const val HOME_SCREEN = "homeScreen"
    const val EMAIL_LINK_SENT_PAGE = "emailLinkSentPage"
    const val RESULT_SCREEN = "resultScreen"
    const val WEB_VIEW_SCREEN = "webViewScreen"
}


@Composable
fun BeforeLoginScreensNavigation(navController: NavController) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = BeforeLoginScreensNavigationObject.AUTH_CHECK
    ) {
        composable(BeforeLoginScreensNavigationObject.AUTH_CHECK) {
            AuthCheckScreen(navController)
        }

        composable(route = BeforeLoginScreensNavigationObject.ONBOARDING_SCREEN) {
            OnBoardingScreen(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.SIGNUP_SCREEN) {
            SignUpScreen(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.LOGIN_SCREEN) {
            LoginScreen(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.HOME_SCREEN) {
            HomeScreen(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.EMAIL_LINK_SENT_PAGE) {
            EmailLinkSentPage(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.EVALUATION_SCREEN) {
            EvaluationScreen(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.RESULT_SCREEN) {
            ResultScreen(navController)
        }
        composable(route = BeforeLoginScreensNavigationObject.WEB_VIEW_SCREEN) {
            WebViewScreen(navController)
        }


    }
}