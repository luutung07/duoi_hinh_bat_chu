package com.example.findword.presentation.main

import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Gravity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.findword.R
import com.example.findword.databinding.MainActvityBinding
import com.example.findword.presentation.BaseActivity
import com.example.findword.presentation.util.AppConfig
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy { WordAdapter() }

    private lateinit var binding: MainActvityBinding

    private var spantCount = AppConfig.SPAN_COUNT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()

        val list = List(16) {
            "Q"
        }

        val newList = list.mapIndexed { index, str ->
            QuestionDisplay(
                word = str,
            )
        }

        adapter.submitList(newList)

    }

    private fun setUpAdapter() {

        binding.rvMainOption.adapter = this@MainActivity.adapter

        for (i in 1..AppConfig.SPAN_COUNT) {
            spantCount *= i;
        }

        binding.rvMainOption.layoutManager = getLayoutManager()
    }

    private fun getLayoutManager(): LayoutManager {
        return FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
        }
    }
}