package com.shettyharshith33.beforeLoginScreens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shettyharshith33.firebaseAuth.AuthUser
import com.shettyharshith33.utils.ResultState
import com.shettyharshith33.vcputtur.R
import com.shettyharshith33.vcputtur.ui.theme.buttonYellow
import com.shettyharshith33.vcputtur.ui.theme.myGreen
import com.shettyharshith33.vcputtur.ui.theme.netWorkRed
import com.shettyharshith33.vcputtur.ui.theme.textColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth



@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val networkViewModel: NetworkViewModel = hiltViewModel() // Initialize NetworkViewModel
    var eMail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var isDialog by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current

    if (isDialog) {
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))
//        NetworkStatusBanner(isConnected = true)

        Spacer(modifier = Modifier.height(10.dp))


        Text(
            "AI Evaluator",
            fontSize = 20.sp,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            "",
            fontSize = 20.sp,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            "",
            fontSize = 15.sp,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(R.drawable.bb_logo),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            "Sign Up",
            fontSize = 30.sp,
            color = textColor
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            "Enter your E-mail",
            fontSize = 15.sp,
            color = textColor,
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .border(
                    0.5.dp, textColor,
                    shape = RoundedCornerShape(5.dp)
                )
                .height(52.dp)
                .width(250.dp),
            value = eMail,
            maxLines = 1,
            onValueChange = {
                eMail = it
                emailError = false
            },
            placeholder = { Text("E-mail") },
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = if (emailError) Color.Red else textColor,
                focusedIndicatorColor = if (emailError) Color.Red else textColor
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Create a password",
            fontSize = 15.sp,
            color = textColor,
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .border(
                    0.5.dp, textColor,
                    shape = RoundedCornerShape(5.dp)
                )
                .height(52.dp)
                .width(250.dp),
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            maxLines = 1,
            placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = if (passwordError) Color.Red else textColor,
                focusedIndicatorColor = if (passwordError) Color.Red else textColor
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if (eMail.isEmpty() || password.isEmpty()) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    emailError = true
                    passwordError = true
                    context.showMsg("Email and Password cannot be empty")
                    triggerVibration(context)
                    return@Button
                }
                scope.launch(Dispatchers.Main) {
                    viewModel.createUser(AuthUser(eMail, password)).collect { result ->
                        when (result) {
                            is ResultState.Success -> {
                                isDialog = false
                                val firebaseUser = viewModel.getCurrentUser()
                                firebaseUser?.let { user ->
                                    user.sendEmailVerification().addOnCompleteListener { task ->
                                        if (task.isSuccessful)
                                        {
                                            context.showMsg("Check your mail for verification")
                                            navController.navigate(BeforeLoginScreensNavigationObject.EMAIL_LINK_SENT_PAGE)
                                        } else {
                                            context.showMsg("Failed to send verification email. Try again.")
                                        }
                                    }
                                }
                            }

                            is ResultState.Failure -> {
                                isDialog = false
                                context.showMsg(result.msg?.message ?: "An error occurred")
                            }

                            is ResultState.Loading -> {
                                isDialog = true
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors().copy(containerColor = buttonYellow)
        ) {
            Text(
                "Create Account",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        // Get screen width and height
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val screenHeight = configuration.screenHeightDp.dp
        // Sign-in with Google Button



    }
}


@Composable
fun NetworkStatusBanner(isConnected: Boolean) {
    var showBackOnlineMessage by remember { mutableStateOf(false) }
    var previousState by remember { mutableStateOf(isConnected) }

    // When network is back, show message for 3 seconds
    LaunchedEffect(isConnected) {
        if (!previousState && isConnected) {
            // If previously offline and now online, show "You are back online"
            showBackOnlineMessage = true
            delay(2000) // Show for 3 seconds
            showBackOnlineMessage = false
        }
        previousState = isConnected
    }

    Column {
        // Show "You are offline" message when disconnected
        AnimatedVisibility(
            visible = !isConnected,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .width(250.dp)
                    .background(netWorkRed)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.offline),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "You are offline",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Show "You are back online" message for a few seconds
        AnimatedVisibility(
            visible = showBackOnlineMessage,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .width(250.dp)
                    .background(myGreen)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Icon(
                        painter = painterResource(R.drawable.online),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "You are back online",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


fun triggerVibration(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}