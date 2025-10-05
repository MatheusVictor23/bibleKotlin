package com.example.bible.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bible.data.local.dao.BibleDao
import com.example.bible.data.local.entity.BookEntity
import com.example.bible.data.local.entity.ChapterEntity
import com.example.bible.data.local.entity.DailyVerseEntity
import com.example.bible.data.local.entity.LastChapterEntity
import com.example.bible.data.local.entity.VerseEntity

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