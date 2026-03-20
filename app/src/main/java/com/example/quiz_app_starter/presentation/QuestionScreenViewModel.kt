package com.example.quiz_app_starter.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quiz_app_starter.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionScreenViewModel(
    questions: List<Question>
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(QuestionScreenState(questions = questions))
    val uiState: StateFlow<QuestionScreenState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeLeft > 0 && _uiState.value.timerRunning) {
                delay(1000L)
                _uiState.update { it.copy(timeLeft = it.timeLeft - 1) }
            }
            if (_uiState.value.timerRunning && _uiState.value.timeLeft == 0) {
                onTimeExpired()
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
        _uiState.update { it.copy(timerRunning = false) }
    }

    private fun resumeTimer() {
        if (_uiState.value.timeLeft > 0 && !_uiState.value.timerRunning && !_uiState.value.showDialog) {
            _uiState.update { it.copy(timerRunning = true) }
            startTimer()
        }
    }

    fun onAnswerSelected(answer: String) {
        _uiState.update { it.copy(selectedAnswer = answer) }
    }

    fun onSubmit() {
        pauseTimer()
        val state = _uiState.value
        val correct = state.selectedAnswer == state.currentQuestion.correctAnswer

        val title: String
        val message: String?

        when {
            state.selectedAnswer == null -> {
                title = "No answer selected."
                message = "The correct answer was: ${state.currentQuestion.correctAnswer}"
            }
            correct -> {
                title = "Correct!"
                message = null
            }
            else -> {
                title = "Wrong!"
                message = "The correct answer was: ${state.currentQuestion.correctAnswer}"
            }
        }

        _uiState.update {
            it.copy(
                showDialog = true,
                dialogTitle = title,
                dialogMessage = message,
                score = if (correct) it.score + 1 else it.score
            )
        }
    }

    fun onNext(): Int? {
        val state = _uiState.value
        return if (state.currentQuestionIndex + 1 < state.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedAnswer = null,
                    timeLeft = 30,
                    timerRunning = true,
                    showDialog = false,
                    dialogTitle = "",
                    dialogMessage = null
                )
            }
            startTimer()
            null
        } else {
            state.score
        }
    }

    private fun onTimeExpired() {
        val state = _uiState.value
        val title: String
        val message: String?

        when {
            state.selectedAnswer == null -> {
                title = "No answer selected.\nTime is out."
                message = "The correct answer was: ${state.currentQuestion.correctAnswer}"
            }
            state.selectedAnswer == state.currentQuestion.correctAnswer -> {
                title = "Correct!"
                message = null
                _uiState.update { it.copy(score = it.score + 1) }
            }
            else -> {
                title = "Wrong!"
                message = "The correct answer was: ${state.currentQuestion.correctAnswer}"
            }
        }

        _uiState.update {
            it.copy(
                timerRunning = false,
                showDialog = true,
                dialogTitle = title,
                dialogMessage = message
            )
        }
    }

    // DefaultLifecycleObserver — pause/resume timer with app lifecycle
    override fun onPause(owner: LifecycleOwner) {
        if (_uiState.value.timerRunning) {
            timerJob?.cancel()
            timerJob = null
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        if (_uiState.value.timerRunning && timerJob == null && !_uiState.value.showDialog) {
            startTimer()
        }
    }
}
