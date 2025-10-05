package com.example.bible.data.local.model

data class BibleJson(
    val abbrev: String,
    val name: String,
    val chapters: List<List<String>>
)