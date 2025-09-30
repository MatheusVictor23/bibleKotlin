package com.example.bible.roomDatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_verses")
data class DailyVerseEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val book: String,
    val chapter: String,
    val verses: String,
    val text: String
)