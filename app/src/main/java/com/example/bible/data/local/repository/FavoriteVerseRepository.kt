package com.example.bible.data.local.repository

import com.example.bible.data.local.dao.FavoriteVerseDao
import com.example.bible.data.local.dao.VerseHighlightDao
import com.example.bible.data.local.entity.FavoriteVerseEntity
import com.example.bible.data.local.model.FavoriteVerse

class FavoriteVerseRepository(private val dao: VerseHighlightDao) {

    suspend fun getAllFavorites(): Map<String, List<FavoriteVerse>> {
        val verses = dao.getAllHighlightedVerses()
        return verses.groupBy { it.bookName }
    }
}