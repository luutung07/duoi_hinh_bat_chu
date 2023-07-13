package com.example.findword.data.repo

import com.example.findword.data.model.Question

interface IQuestionRepo {

    fun getListQuestion(): List<Question>

    fun getQuestion(index: Int): Question

}