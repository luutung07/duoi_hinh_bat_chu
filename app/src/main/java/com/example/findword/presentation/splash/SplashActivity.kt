package com.example.findword.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.findword.R
import com.example.findword.databinding.MainActvityBinding
import com.example.findword.databinding.SplashActivityBinding
import com.example.findword.presentation.BaseActivity
import com.example.findword.presentation.main.MainActivity
import com.example.findword.presentation.util.AppConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(AppConfig.TIME_DELAY_NAVIGATION)
            navigation(MainActivity())
        }

    }
}