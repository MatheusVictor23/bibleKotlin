package com.example.bible.data.local.repository

import androidx.compose.ui.graphics.Color
import com.example.bible.data.local.dao.VerseHighlightDao
import com.example.bible.data.local.entity.VerseHighlightEntity

class VerseHighlightRepository(private val dao: VerseHighlightDao) {

    suspend fun addHighlight(verseId: Int, color: Color) {
        val colorHex = "#%02x%02x%02x".format(
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
        dao.insertHighlight(VerseHighlightEntity(verseId = verseId, color = colorHex))
    }

    suspend fun removeHighlight(verseId: Int) {
        dao.deleteHighlight(verseId)
    }

    suspend fun getHighlights(): Map<Int, Color> {
        return dao.getAllHighlightedVerses().associate { entity ->
            val color = Color(android.graphics.Color.parseColor(entity.highlightColor))
            entity.verseId to color
        }
    }
}