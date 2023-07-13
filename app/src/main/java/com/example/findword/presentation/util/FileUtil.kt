package com.example.findword.presentation.util

import com.example.findword.extensions.getApplication

object FileUtil {

    fun readFileJsonFromAssets(): String{
        return getApplication().assets.open(AppConfig.FILE_NAME).bufferedReader().use { it.readText() }
    }

}