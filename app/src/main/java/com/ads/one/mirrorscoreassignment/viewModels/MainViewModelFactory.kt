package com.ads.one.mirrorscoreassignment.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ads.one.mirrorscoreassignment.repository.QuizRepository

class MainViewModelFactory(
    private val repository: QuizRepository,
    private val amount: Int,
    private val category: Int,
    private val difficulty: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository,amount,category,difficulty) as T
    }
}