package com.example.quiz_app_starter.data.remote

import com.google.gson.annotations.SerializedName

data class TriviaQuestionDto(
    val category: String,
    val id: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val tags: List<String>,
    val question: String,
    val type: String,
    val difficulty: String,
    val regions: List<String>?,
    @SerializedName("isNiche") val isNiche: Boolean
)
