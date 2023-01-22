package com.ads.one.mirrorscoreassignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ads.one.mirrorscoreassignment.api.QuizService
import com.ads.one.mirrorscoreassignment.models.QuizList

class QuizRepository(private val quizService: QuizService) {
    private val quizLiveData = MutableLiveData<QuizList>()
    val quiz: LiveData<QuizList>
        get() = quizLiveData

    suspend fun getQuiz(amount: Int, category: Int, difficulty: String, type: String) {
        val result = quizService.getQuiz(amount, category, difficulty, type)
        if (result.body() != null) {
            quizLiveData.postValue(result.body())
        }
    }
}