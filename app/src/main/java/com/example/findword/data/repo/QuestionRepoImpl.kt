package com.example.findword.data.repo

import com.example.findword.data.model.ListQuestion
import com.example.findword.data.model.Question
import com.example.findword.presentation.util.FileUtil
import com.google.gson.Gson

class QuestionRepoImpl : IQuestionRepo {

    private val dataJson = FileUtil.readFileJsonFromAssets()
    private val gson = Gson()
    private val questionResponse = gson.fromJson<ListQuestion>(dataJson, ListQuestion::class.java)

    override fun getListQuestion(): List<Question> {
        return questionResponse.question ?: throw Exception("question has exception")
    }

    override fun getQuestion(index: Int): Question {
        if (questionResponse.question.isNullOrEmpty()) {
            throw Exception("question has exception")
        } else {
            if (index < 0 || index >= questionResponse.question!!.size) {
                throw Exception("index vượt quá vị trí trong list question")
            } else {
                return questionResponse.question!![index]
            }
        }
    }
}