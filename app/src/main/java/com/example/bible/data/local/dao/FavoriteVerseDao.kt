package com.example.bible.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bible.data.local.entity.FavoriteVerseEntity
import com.example.bible.data.local.model.FavoriteVerse

@Dao
interface FavoriteVerseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteVerseEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteVerseEntity)

    @Query("SELECT * FROM favorite_verses")
    suspend fun getAllFavorites(): List<FavoriteVerseEntity>

    @Query("""
        SELECT verses.*, favorite_verses.color
        FROM verses
        INNER JOIN favorite_verses
        ON verses.id = favorite_verses.verseId
        INNER JOIN chapters
        ON verses.chapterId = chapters.id
        INNER JOIN books
        ON chapters.bookId = books.id
        ORDER BY books.name, verses.chapterId, verses.number
    """)
    suspend fun getFavoriteVersesWithBook(): List<FavoriteVerse>
}