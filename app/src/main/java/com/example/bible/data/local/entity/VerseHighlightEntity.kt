package com.example.bible.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "verse_highlights",
    foreignKeys = [
        ForeignKey(
            entity = VerseEntity::class,
            parentColumns = ["id"],
            childColumns = ["verseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("verseId", unique = true)]
)
data class VerseHighlightEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val verseId: Int,
    val color: String // ex: "#FFEB3B"
)
