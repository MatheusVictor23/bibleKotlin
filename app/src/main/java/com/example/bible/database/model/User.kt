package com.example.bible.database.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val level: Int = 1,
    var totalPoints: Int = 0,
    var points: Int = 0,
    val lives: Int = 3,
    val daysStreak: Int = 1,
    val isLeader: Boolean = false,
    val lastLifeLost: String = "",
    val lastLifeRegenerated: String = "",
    val createdAt: String = ""
)

