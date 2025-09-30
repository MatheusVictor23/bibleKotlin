package com.example.bible.roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.bible.roomDatabase.entity.BookEntity
import com.example.bible.roomDatabase.entity.ChapterEntity
import com.example.bible.roomDatabase.entity.LastChapterEntity
import com.example.bible.roomDatabase.entity.VerseEntity


@Dao
interface BibleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(verses: List<VerseEntity>)

    @Transaction
    @Query("""
        SELECT verses.id, verses.chapterId, verses.number, verses.text
        FROM verses 
        INNER JOIN chapters ON verses.chapterId = chapters.id
        WHERE chapters.bookId = :bookId AND chapters.number = :chapterNumber
        ORDER BY verses.number ASC
    """)
    suspend fun getVersesByBookAndChapter(bookId: Int, chapterNumber: Int): List<VerseEntity>

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

    @Query("SELECT * FROM chapters WHERE bookId = :bookId")
    suspend fun getChaptersByBook(bookId: Int): List<ChapterEntity>

    @Query("SELECT * FROM last_chapter LIMIT 1")
    suspend fun getLastChapter(): LastChapterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastChapter(lecture:LastChapterEntity)
}
