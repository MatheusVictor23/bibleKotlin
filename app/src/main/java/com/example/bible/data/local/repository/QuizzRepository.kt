package com.example.bible.data.local.repository

import com.example.bible.data.local.dao.QuizzDao
import com.example.bible.data.local.entity.QuizzEntity

class QuizzRepository(private val dao: QuizzDao) {

    suspend fun getQuizzesByChapter(chapterId: Int?): List<QuizzEntity> {
        val questions = dao.getQuizzesByChapter(chapterId)
        println("📘 DAO retornou ${questions.size} perguntas para o capítulo $chapterId")
        return questions
    }

    suspend fun insertQuizzes(quizzes: List<QuizzEntity>) {
        dao.insertAll(quizzes)
    }

    suspend fun clearQuizzes() {
        dao.clearAll()
    }
}