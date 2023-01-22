package com.ads.one.mirrorscoreassignment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ads.one.mirrorscoreassignment.models.QuizList
import com.ads.one.mirrorscoreassignment.repository.QuizRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: QuizRepository,
    amount: Int,
    category: Int,
    difficulty: String
) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.getQuiz(amount, category, difficulty, "multiple")
        }
    }

    val quizzes: LiveData<QuizList>
        get() = repository.quiz
}