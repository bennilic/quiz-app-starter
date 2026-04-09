package com.example.quiz_app_starter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val category: String,
    val correctAnswer: String,
    val answers: List<String>,
    val tags: List<String>,
    val question: String,
    val type: String,
    val difficulty: String,
    val regions: List<String>?,
    val isNiche: Boolean
)
