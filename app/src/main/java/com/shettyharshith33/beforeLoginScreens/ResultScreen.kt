package com.yourpackage.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    navController: NavController,
    totalQuestions: Int = 10,
    attemptedQuestions: Int = 8,
    score: Int = 15,
    maxScore: Int = 20,
    registerNumber: String = "23CS123"
) {
    val percentage = (score.toFloat() / maxScore.toFloat()) * 100f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Evaluation Report",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Circular score indicator
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = percentage / 100f,
                strokeWidth = 12.dp,
                modifier = Modifier.size(180.dp),
                color = Color(0xFF4CAF50)
            )
            Text(
                text = "$score / $maxScore",
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Details card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Register Number: $registerNumber")
                Text("Total Questions: $totalQuestions")
                Text("Attempted Questions: $attemptedQuestions")
                Text("Score: $score")
                Text("Accuracy: ${percentage.toInt()}%")
                Text("Remarks: ${
                    when {
                        percentage > 75 -> "Excellent"
                        percentage > 50 -> "Good"
                        else -> "Needs Improvement"
                    }
                }")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Back to Home")
        }
    }
}
