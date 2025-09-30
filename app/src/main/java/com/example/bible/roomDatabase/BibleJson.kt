package com.example.bible.roomDatabase

data class BibleJson(
    val abbrev: String,
    val name: String,
    val chapters: List<List<String>>
)