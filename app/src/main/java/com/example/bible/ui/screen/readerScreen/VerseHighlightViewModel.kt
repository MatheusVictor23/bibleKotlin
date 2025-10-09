package com.example.bible.ui.screen.readerScreen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bible.data.local.entity.VerseEntity
import com.example.bible.data.local.repository.VerseHighlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class VerseHighlightViewModel(
    private val repository: VerseHighlightRepository
) : ViewModel() {

    private val _highlights = MutableStateFlow<Map<Int, Color>>(emptyMap())
    val highlights = _highlights

    init {
        loadHighlights()
    }

    fun loadHighlights() {
        viewModelScope.launch {
            _highlights.value = repository.getHighlights()
        }
    }

    fun markVerses(verses: List<VerseEntity>, color: Color) {
        viewModelScope.launch {
            verses.forEach { verse ->
                repository.addHighlight(verse.id, color)
            }
            loadHighlights()
        }
    }

    fun unmarkVerse(verseId: Int) {
        viewModelScope.launch {
            repository.removeHighlight(verseId)
            _highlights.value = _highlights.value - verseId
        }
    }
}