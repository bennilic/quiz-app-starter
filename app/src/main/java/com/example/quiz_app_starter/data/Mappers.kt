package com.example.quiz_app_starter.data

import com.example.quiz_app_starter.data.local.QuestionEntity
import com.example.quiz_app_starter.data.remote.TriviaQuestionDto
import com.example.quiz_app_starter.model.Question

fun TriviaQuestionDto.toEntity(): QuestionEntity {
    val shuffledAnswers = (incorrectAnswers + correctAnswer).shuffled()
    return QuestionEntity(
        id = id,
        category = category,
        correctAnswer = correctAnswer,
        answers = shuffledAnswers,
        tags = tags,
        question = question,
        type = type,
        difficulty = difficulty,
        regions = regions,
        isNiche = isNiche
    )
}

fun QuestionEntity.toDomain(): Question = Question(
    id = id,
    category = category,
    correctAnswer = correctAnswer,
    answers = answers,
    tags = tags,
    question = question,
    type = type,
    difficulty = difficulty,
    regions = regions,
    isNiche = isNiche
)
