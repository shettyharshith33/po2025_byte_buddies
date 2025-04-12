package com.shettyharshith33.beforeLoginScreens

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel

class FirebaseApp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}