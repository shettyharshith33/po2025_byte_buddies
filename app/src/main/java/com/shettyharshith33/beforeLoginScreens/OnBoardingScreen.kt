package com.shettyharshith33.beforeLoginScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shettyharshith33.vcputtur.R
import com.shettyharshith33.vcputtur.ui.theme.myGrey
import com.shettyharshith33.vcputtur.ui.theme.poppinsFontFamily
import com.shettyharshith33.vcputtur.ui.theme.textColor
import com.shettyharshith33.vcputtur.ui.theme.themeBlue


@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: NetworkViewModel = hiltViewModel()
) {
    val isConnected by viewModel.isConnected.observeAsState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.welcome_animation))

    // Get screen width and height
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current


    Spacer(modifier = Modifier.height(20.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = screenWidth * 0.05f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(screenHeight * 0.05f))
        NetworkStatusBanner(isConnected ?: false)

        // College Name
        Text(
            "AI Evaluator",
            fontFamily = poppinsFontFamily,
            fontSize = (screenWidth.value * 0.05f).sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
//        Text(
//            "Arts, Science and Commerce",
//            fontSize = (screenWidth.value * 0.05f).sp,
//            color = textColor,
//            fontWeight = FontWeight.Bold
//        )
//        Text(
//            "(Autonomous)",
//            fontSize = (screenWidth.value * 0.04f).sp,
//            color = textColor,
//            fontWeight = FontWeight.Bold
//        )

        Spacer(modifier = Modifier.height(screenHeight * 0.02f))

        // College Logo
        Image(
            modifier = Modifier.size(screenWidth * 0.2f),
            painter = painterResource(R.drawable.bb_logo),
            contentDescription = null
        )

        // Animation
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(screenWidth * 0.8f),
            iterations = LottieConstants.IterateForever
        )
        Text("Login with your e-mail and password", color = Color.Black, fontSize = (screenWidth.value * 0.04f).sp)

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))

        // Login Button
        Button(
            onClick = {
                navController.navigate(BeforeLoginScreensNavigationObject.LOGIN_SCREEN)
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(screenHeight * 0.06f)
                .defaultMinSize(minWidth = 200.dp, minHeight = 48.dp)
                .widthIn(max = 400.dp), // Ensures button doesn't get too large
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Login", color = Color.White, fontSize = (screenWidth.value * 0.045f).sp)
        }

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))


        // Sign-up Button
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(screenHeight * 0.06f)
                .widthIn(max = 400.dp),
            onClick = {
                navController.navigate(BeforeLoginScreensNavigationObject.SIGNUP_SCREEN)
            },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                "Don't have an account? Sign-up here",
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = (screenWidth.value * 0.035f).sp, // Smaller to prevent line breaks
                maxLines = 1
            )
        }
    }
}
