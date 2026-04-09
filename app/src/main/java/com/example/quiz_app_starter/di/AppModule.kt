package com.example.quiz_app_starter.di

import android.content.Context
import androidx.room.Room
import com.example.quiz_app_starter.data.local.QuestionDao
import com.example.quiz_app_starter.data.local.QuestionDatabase
import com.example.quiz_app_starter.data.remote.TriviaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://the-trivia-api.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideTriviaApiService(retrofit: Retrofit): TriviaApiService =
        retrofit.create(TriviaApiService::class.java)

    @Provides
    @Singleton
    fun provideQuestionDatabase(@ApplicationContext context: Context): QuestionDatabase =
        Room.databaseBuilder(context, QuestionDatabase::class.java, "quiz_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideQuestionDao(db: QuestionDatabase): QuestionDao = db.questionDao()
}
