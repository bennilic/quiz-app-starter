package com.example.quiz_app_starter.presentation

import com.example.quiz_app_starter.model.Question

data class QuestionScreenState(
    val questions: List<Question>,
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val timeLeft: Int = 30,
    val timerRunning: Boolean = true,
    val showDialog: Boolean = false,
    val dialogTitle: String = "",
    val dialogMessage: String? = null,
    val score: Int = 0
) {
    val currentQuestion: Question
        get() = questions[currentQuestionIndex]

    val timerProgress: Float
        get() = timeLeft.toFloat() / 30f
}
