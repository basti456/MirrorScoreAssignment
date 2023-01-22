package com.ads.one.mirrorscoreassignment.api

import com.ads.one.mirrorscoreassignment.models.QuizList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


//https://opentdb.com/api.php?amount=10&category=21&difficulty=hard&type=multiple

interface QuizService {
    @GET("/api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String
    ): Response<QuizList>
}