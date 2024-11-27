package com.example.roomproject.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Aquí puedes inicializar otras bibliotecas globales si es necesario.
    }
}