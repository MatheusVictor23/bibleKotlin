package com.example.bible.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Level(
    val id: Int,
    val level: Int,
    val requiredPoints: Int
)