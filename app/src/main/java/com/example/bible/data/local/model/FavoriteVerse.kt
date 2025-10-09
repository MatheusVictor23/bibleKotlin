package com.example.bible.data.local.model

data class FavoriteVerse(
    val verseId: Int,
    val verseNumber: Int,
    val verseText: String,
    val chapterId: Int,
    val chapterNumber: Int,
    val bookId: Int,
    val bookName: String,
    val highlightColor: String
)