package com.example.quiz_app_starter.presentation

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quiz_app_starter.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionScreenViewModel(questions: List<Question>) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(QuestionScreenState(questions = questions))
    val uiState: StateFlow<QuestionScreenState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeLeft > 0) {
                delay(1000L)
                _uiState.update { it.copy(timeLeft = it.timeLeft - 1) }
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun resumeTimer() {
        val state = _uiState.value
        if (timerJob == null && state.timeLeft > 0 && !state.showResultDialog) {
            startTimer()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        pauseTimer()
    }

    override fun onStart(owner: LifecycleOwner) {
        resumeTimer()
    }

    fun onAnswerSelected(answer: String) {
        _uiState.update { it.copy(selectedAnswer = answer) }
    }

    fun onSubmit() {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        pauseTimer()
        val isCorrect = state.selectedAnswer == question.correctAnswer
        val newPoints = if (isCorrect) state.pointsAchieved + 1 else state.pointsAchieved
        val message = if (isCorrect) "Correct!" else "Wrong! Correct answer: ${question.correctAnswer}"
        _uiState.update {
            it.copy(
                pointsAchieved = newPoints,
                showResultDialog = true,
                dialogMessage = message
            )
        }
    }

    fun onDismissDialog() {
        val nextIndex = _uiState.value.currentQuestionIndex + 1
        if (nextIndex < _uiState.value.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    selectedAnswer = null,
                    timeLeft = TIMER_DURATION,
                    showResultDialog = false,
                    dialogMessage = ""
                )
            }
            startTimer()
        } else {
            _uiState.update { it.copy(showResultDialog = false) }
        }
    }

    companion object {
        fun Factory(questions: List<Question>): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    QuestionScreenViewModel(questions) as T
            }
    }
}
