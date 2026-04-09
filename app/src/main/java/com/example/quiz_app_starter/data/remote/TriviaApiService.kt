package com.example.quiz_app_starter.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {

    @GET("questions")
    suspend fun getQuestions(
        @Query("limit") limit: Int = 10
    ): List<TriviaQuestionDto>
}
