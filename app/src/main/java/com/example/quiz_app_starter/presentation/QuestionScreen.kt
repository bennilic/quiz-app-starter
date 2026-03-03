package com.example.quiz_app_starter.presentation

import androidx.compose.runtime.Composable
import com.example.quiz_app_starter.model.Question
import com.example.quiz_app_starter.model.getDummyQuestions

@Composable
fun QuestionScreen(
    questions: List<Question> = getDummyQuestions(),
    currentQuestionIndex: Int = 0,
){

}