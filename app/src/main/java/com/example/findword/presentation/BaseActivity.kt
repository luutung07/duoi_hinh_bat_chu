package com.example.findword.presentation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    private fun closeApp() {
        this.finishAffinity()
    }

    override fun onBackPressed() {
        closeApp()
        super.onBackPressed()
    }

    fun navigation(activity: BaseActivity) {
        startActivity(Intent(this, activity::class.java))
    }
}