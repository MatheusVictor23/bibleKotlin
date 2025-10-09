package com.example.bible.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bible.data.local.entity.QuizzEntity

@Dao
interface QuizzDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quizzes: List<QuizzEntity>)

    @Query("SELECT * FROM quizzes WHERE chapterId = :chapterId")
    suspend fun getQuizzesByChapter(chapterId: Int?): List<QuizzEntity>

    @Query("SELECT * FROM quizzes")
    suspend fun getAll(): List<QuizzEntity>

    @Query("DELETE FROM quizzes")
    suspend fun clearAll()
}