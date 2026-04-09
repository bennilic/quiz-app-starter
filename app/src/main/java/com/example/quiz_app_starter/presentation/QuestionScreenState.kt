package com.example.quiz_app_starter.presentation

import com.example.quiz_app_starter.model.Question

const val TIMER_DURATION = 30

data class QuestionScreenState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val timeLeft: Int = TIMER_DURATION,
    val showResultDialog: Boolean = false,
    val dialogMessage: String = "",
    val pointsAchieved: Int = 0
) {
    val currentQuestion: Question? get() = questions.getOrNull(currentQuestionIndex)
    val timerProgress: Float get() = timeLeft.toFloat() / TIMER_DURATION.toFloat()
}
