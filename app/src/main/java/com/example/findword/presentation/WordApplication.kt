package com.example.findword.presentation

import android.app.Application
import com.example.findword.extensions.setApplication

class WordApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }
}