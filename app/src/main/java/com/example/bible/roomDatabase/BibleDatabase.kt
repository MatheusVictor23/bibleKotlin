package com.example.bible.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bible.roomDatabase.dao.BibleDao
import com.example.bible.roomDatabase.entity.BookEntity
import com.example.bible.roomDatabase.entity.ChapterEntity
import com.example.bible.roomDatabase.entity.DailyVerseEntity
import com.example.bible.roomDatabase.entity.LastChapterEntity
import com.example.bible.roomDatabase.entity.VerseEntity

@Database(
    entities = [
        BookEntity::class,
        ChapterEntity::class,
        VerseEntity::class,
        LastChapterEntity::class,
        DailyVerseEntity::class
    ],
    version = 1,
    exportSchema = true,

    )
abstract class BibleDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao

}