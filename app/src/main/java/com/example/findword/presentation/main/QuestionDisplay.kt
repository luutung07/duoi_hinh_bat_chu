package com.example.findword.presentation.main

import android.util.Size

class QuestionDisplay(

    var character: String? = null,

    var isSelect: Boolean = false,

    var type: WORD_TYPE? = null,

    var size: Size? = null,

    var error: Boolean = false,

    var answerCorrect: String? = null
)

enum class WORD_TYPE {
    ANSWER, OPTION, SELECTED_OPTION
}

