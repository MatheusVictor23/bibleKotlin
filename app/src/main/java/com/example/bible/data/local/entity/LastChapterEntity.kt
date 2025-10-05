package com.example.bible.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_chapter")
data class LastChapterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val chapterNumber: Int
)
