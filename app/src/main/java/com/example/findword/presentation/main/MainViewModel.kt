package com.example.findword.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findword.data.model.Question
import com.example.findword.data.repo.IQuestionRepo
import com.example.findword.data.repo.QuestionRepoImpl
import com.example.findword.extensions.INT_DEFAULT
import com.example.findword.extensions.STRING_DEFAULT
import com.example.findword.factory.RepositoryFactory
import com.example.findword.presentation.StateResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    private var _questionState = MutableStateFlow<StateResource<Question>>(StateResource.Init())
    val questionState = _questionState.asStateFlow()

    private var _answerState = MutableStateFlow<StateResource<List<String>>>(StateResource.Init())
    val answerState = _answerState.asStateFlow()

    private var _optionState = MutableStateFlow<StateResource<List<String>>>(StateResource.Init())
    val optionState = _optionState.asStateFlow()

    private val repo = RepositoryFactory.getQuestionRepo()

    var currentQuestion = 12

    var answerCorrect: String = STRING_DEFAULT

    var indexAnswer = -1

    var savePositionAnswer: MutableMap<Int, Int> = hashMapOf()

    var saveAnswerRemove: MutableMap<Int, Int> = hashMapOf()

    var position = -1

    var savePositionOption: MutableMap<Int, String> = hashMapOf()

    init {
        getQuestion()
    }

    private fun addCharacterOtherCorrectWord(): List<String> {
        val listCharacter = mutableListOf<String>()
        listCharacter.addAll(repo.getAllCharacter())
        answerCorrect.forEach {
            listCharacter.remove(it.toString())
        }
        return listCharacter.subList(0, (0..5).random())
    }

    fun getQuestion() {
        viewModelScope.launch {
            _questionState.value = StateResource.Loading()
            try {
                val question = repo.getQuestion(currentQuestion)
                answerCorrect = question.character ?: STRING_DEFAULT

                /**
                 * list answer
                 */
                val listCharacterCorrect = mutableListOf<String>()
                answerCorrect.forEach {
                    listCharacterCorrect.add(it.toString())
                }
                _answerState.value = StateResource.Success(data = listCharacterCorrect)


                /**
                 * list option
                 */
                val listOption = mutableListOf<String>()
                listOption.addAll(listCharacterCorrect)
                listOption.addAll(addCharacterOtherCorrectWord())
                _optionState.value = StateResource.Success(data = listOption)

                _questionState.value = StateResource.Success(data = question)
            } catch (e: Exception) {
                _questionState.value = StateResource.Error(throwable = e)
            }
        }
    }

    fun selectWord(option: String, type: WORD_TYPE, position: Int) {
        viewModelScope.launch {

            savePositionOption[position] = option

            val listAnswer = _answerState.value.data?.toMutableList()

            val listOption = _optionState.value.data?.toMutableList()

            when (type) {
                WORD_TYPE.SELECTED_OPTION -> {

                }

                WORD_TYPE.OPTION -> {

                    if (saveAnswerRemove.containsKey(position)) {
                        val index = saveAnswerRemove[position]
                        savePositionAnswer[index!!] = position
                        listAnswer?.set(index!!, option)
                    } else {
                        indexAnswer++
                        savePositionAnswer[indexAnswer] = position
                        listAnswer?.set(indexAnswer, option)
                    }

                    listAnswer?.let {
                        _answerState.value = StateResource.Success(data = it)
                    }

                    listOption?.let {
                        _optionState.value = StateResource.Success(data = it)
                    }
                }

                else -> {}
            }
        }
    }

    fun removeWord(position: Int) {
        viewModelScope.launch {
            indexAnswer--
            val listAnswer = _answerState.value.data?.toMutableList()

            val listOption = _optionState.value.data?.toMutableList()

            savePositionOption.remove(position)

            var key = -1
            savePositionAnswer.forEach { k, v ->
                if (v == position) {
                    key = k
                }
            }

            savePositionAnswer.remove(key)

            saveAnswerRemove[position] = key

            listAnswer?.let {
                if (key in 0 until it.size) {
                    it[key] = STRING_DEFAULT
                }
                _answerState.value = StateResource.Success(data = it)
            }

            listOption?.let {
                _optionState.value = StateResource.Success(data = it)
            }
        }
    }
}