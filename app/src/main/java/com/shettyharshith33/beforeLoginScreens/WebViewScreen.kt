package com.shettyharshith33.beforeLoginScreens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun WebViewScreen(navController: NavController)
{
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()  // To keep navigation inside the WebView
            loadUrl("http://127.0.0.1:5000/")
        }
    })

}