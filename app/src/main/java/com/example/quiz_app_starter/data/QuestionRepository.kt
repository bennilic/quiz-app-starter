package com.example.quiz_app_starter.data

import com.example.quiz_app_starter.data.local.QuestionDao
import com.example.quiz_app_starter.data.remote.TriviaApiService
import com.example.quiz_app_starter.model.Question
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    private val api: TriviaApiService,
    private val dao: QuestionDao
) {
    suspend fun getQuestions(): List<Question> {
        return try {
            val dtos = api.getQuestions()
            val entities = dtos.map { it.toEntity() }
            dao.deleteAll()
            dao.insertAll(entities)
            entities.map { it.toDomain() }
        } catch (e: Exception) {
            dao.getAll().map { it.toDomain() }
        }
    }
}
