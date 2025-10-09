package com.example.bible.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_verses",
    foreignKeys = [
        ForeignKey(
            entity = VerseEntity::class,
            parentColumns = ["id"],
            childColumns = ["verseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["verseId"], unique = true)]
)
data class FavoriteVerseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val verseId: Int,
    val color: String // Armazenamos como string em formato HEX, ex: "#FFEB3B"
)