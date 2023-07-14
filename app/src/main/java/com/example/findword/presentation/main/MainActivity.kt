package com.example.findword.presentation.main

import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.findword.R
import com.example.findword.databinding.MainActvityBinding
import com.example.findword.extensions.getImage
import com.example.findword.presentation.BaseActivity
import com.example.findword.presentation.STATE_TYPE
import com.example.findword.presentation.util.AppConfig
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent


class MainActivity : BaseActivity() {

    companion object {
        private const val TAG = "MAIN"
    }

    private val viewModel by viewModels<MainViewModel>()

    private val adapterOption by lazy { WordAdapter() }

    private val adapterResult by lazy { WordAdapter() }

    private lateinit var binding: MainActvityBinding

    private var itemRow = AppConfig.SPAN_COUNT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setEventView()
        setUpAdapter()
        observerData()
    }

    private fun setEventView() {

    }

    private fun setUpAdapter() {
        /**
         * answer
         */
        binding.rvMainResult.adapter = this@MainActivity.adapterResult
        binding.rvMainResult.layoutManager = getLayoutManager()

        /**
         * option
         */
        binding.rvMainOption.adapter = this@MainActivity.adapterOption
        binding.rvMainOption.layoutManager = getLayoutManager()

        addListener()
    }

    private fun addListener() {
        adapterResult.listener = object : IWordListener {
            override fun onRemoveOption(position: Int, type: WORD_TYPE, option: String?) {
                viewModel.removeWord(position, type, option)
            }
        }

        adapterOption.listener = object : IWordListener {
            override fun onSelectOption(option: String, type: WORD_TYPE, position: Int) {
                Log.d(TAG, "onSelectOption: tunglv ${viewModel.indexAnswer}")
                if (viewModel.indexAnswer >= viewModel.answerCorrect.length - 1) {
                    Toast.makeText(this@MainActivity, "chàn ký tự", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.selectWord(option, type, position)
                }
            }

            override fun onRemoveOption(position: Int, type: WORD_TYPE, option: String?) {
                viewModel.removeWord(position, type, option)
            }
        }
    }

    private fun getLayoutManager(): LayoutManager {
        return FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
        }
    }

    private fun observerData() {
        lifecycleScope.launchWhenCreated {
            viewModel.questionState.collect {
                when (it.stateType) {
                    STATE_TYPE.ERROR -> {

                    }

                    STATE_TYPE.SUCCESS -> {
                        with(binding) {
                            ivMainImageLeft.setImageDrawable(getImage("h${it.data?.id}_1"))
                            ivMainImageRight.setImageDrawable(getImage("h${it.data?.id}_2"))
                        }
                    }

                    else -> {
                        // TODO: dothing 
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.answerState.collect {
                when (it.stateType) {
                    STATE_TYPE.ERROR -> {}
                    STATE_TYPE.SUCCESS -> {
                        // answer
                        val listAnswer = it.data?.mapIndexed { i, v ->

                            val type = if (viewModel.savePositionAnswer.containsKey(i)) {
                                WORD_TYPE.SELECTED_OPTION
                            } else {
                                WORD_TYPE.ANSWER
                            }

                            QuestionDisplay(
                                isSelect = false,
                                character = v,
                                type = type,
                                size = getSizeItem()
                            )
                        }
                        adapterResult.submitList(listAnswer)
                    }

                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.optionState.collect {
                when (it.stateType) {
                    STATE_TYPE.ERROR -> {}
                    STATE_TYPE.SUCCESS -> {
                        // option
                        val listoption = it.data?.mapIndexed { i, v ->

                            val select = viewModel.savePositionOption.containsKey(i)

                            QuestionDisplay(
                                isSelect = select,
                                character = v,
                                type = WORD_TYPE.OPTION,
                                size = getSizeItem(),
                                answerCorrect = viewModel.answerCorrect
                            )
                        }
                        adapterOption.submitList(listoption)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getSizeItem(): Size {
        val screenSize = resources.displayMetrics.widthPixels
        val itemPadding = resources.getDimensionPixelSize(R.dimen.dimen_2)
        val width = (screenSize - (itemRow * 3 * itemPadding)) / itemRow
        val height = width // Ratio 1:1
        return Size(width, height)
    }
}