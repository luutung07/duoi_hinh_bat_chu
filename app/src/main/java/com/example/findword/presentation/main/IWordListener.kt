package com.example.findword.presentation.main

interface IWordListener {
    fun onSelectOption(option: String,type: WORD_TYPE,position: Int)

    fun onRemoveOption(position: Int)
}