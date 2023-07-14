package com.example.findword.factory

import com.example.findword.data.repo.IQuestionRepo
import com.example.findword.data.repo.QuestionRepoImpl

object RepositoryFactory {

    private val questionRepoImpl by lazy {
        QuestionRepoImpl()
    }

    init {

    }

    fun getQuestionRepo(): IQuestionRepo {
        return questionRepoImpl
    }
}