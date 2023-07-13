package com.example.findword.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findword.data.model.Question
import com.example.findword.data.repo.IQuestionRepo
import com.example.findword.data.repo.QuestionRepoImpl
import com.example.findword.presentation.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {

    private var _questionState = MutableStateFlow<StateResource<Question>>(StateResource.Init())
    val questionState = _questionState.asStateFlow()

    var currentQuestion = 0

    init {
        getQuestion()
    }

    fun getQuestion(){
        viewModelScope.launch(Dispatchers.IO) {
            _questionState.value = StateResource.Loading()
            val repo: IQuestionRepo = QuestionRepoImpl()
            try {
                _questionState.value = StateResource.Success(data = repo.getQuestion(currentQuestion))
            }catch (e: Exception){
                _questionState.value = StateResource.Error(throwable = e)
            }
        }
    }
}