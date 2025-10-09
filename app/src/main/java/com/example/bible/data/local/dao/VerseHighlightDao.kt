package com.example.bible.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bible.data.local.entity.VerseHighlightEntity
import com.example.bible.data.local.model.FavoriteVerse

@Dao
interface VerseHighlightDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: VerseHighlightEntity)

    @Query("""
        SELECT v.id AS verseId,
               v.number AS verseNumber,
               v.text AS verseText,
               c.id AS chapterId,
               c.number AS chapterNumber,
               b.id AS bookId,
               b.name AS bookName,
               h.color AS highlightColor
        FROM verse_highlights h
        INNER JOIN verses v ON v.id = h.verseId
        INNER JOIN chapters c ON v.chapterId = c.id
        INNER JOIN books b ON c.bookId = b.id
        ORDER BY b.id, c.number, v.number
    """)
    suspend fun getAllHighlightedVerses(): List<FavoriteVerse>
    @Query("DELETE FROM verse_highlights WHERE verseId = :verseId")
    suspend fun deleteHighlight(verseId: Int)
}