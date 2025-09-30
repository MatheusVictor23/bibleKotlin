package com.example.bible.roomDatabase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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