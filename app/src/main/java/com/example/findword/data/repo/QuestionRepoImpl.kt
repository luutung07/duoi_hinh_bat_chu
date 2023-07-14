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
                val question = questionResponse.question!![index]
                question.character = question.character?.replace("_", "")?.toRegex().toString()
                return question
            }
        }
    }

    override fun getAllCharacter(): List<String> {
        val list = mutableListOf<String>()
        list.add("A")
        list.add("B")
        list.add("C")
        list.add("D")
        list.add("E")
        list.add("F")
        list.add("G")
        list.add("H")
        list.add("I")
        list.add("J")
        list.add("K")
        list.add("L")
        list.add("M")
        list.add("N")
        list.add("O")
        list.add("P")
        list.add("Q")
        list.add("R")
        list.add("S")
        list.add("T")
        list.add("U")
        list.add("V")
        list.add("W")
        list.add("X")
        list.add("Y")
        list.add("Z")
        return list
    }
}