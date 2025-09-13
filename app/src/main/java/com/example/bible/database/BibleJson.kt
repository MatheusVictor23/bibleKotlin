package com.example.bible.database

data class BibleJson(
    val abbrev: String,
    val name: String,
    val chapters: List<List<String>>
)