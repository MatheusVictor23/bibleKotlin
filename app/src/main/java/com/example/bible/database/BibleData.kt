package com.example.bible.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val abbrev: String,
    val name: String
)


@Entity(
    tableName = "chapters",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ChapterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val number: Int
)


@Entity(
    tableName = "verses",
    foreignKeys = [
        ForeignKey(
            entity = ChapterEntity::class,
            parentColumns = ["id"],
            childColumns = ["chapterId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VerseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val chapterId: Int,
    val number: Int,
    val text: String
)



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
}




@Database(
    entities = [BookEntity::class, ChapterEntity::class, VerseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BibleDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao
}