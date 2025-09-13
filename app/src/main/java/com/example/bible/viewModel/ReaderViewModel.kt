package com.example.bible.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bible.database.BibleRepository
import com.example.bible.database.BookEntity
import com.example.bible.database.VerseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReaderViewModel(private val repository: BibleRepository): ViewModel() {

    private val _books = MutableStateFlow<List<BookEntity>>(emptyList())
    val books: StateFlow<List<BookEntity>> = _books

    private val _verses = MutableStateFlow<List<VerseEntity>>(emptyList())
    val verses: StateFlow<List<VerseEntity>> = _verses

    init {
        // Carrega os dados no início
        viewModelScope.launch {
            // Se o banco estiver vazio, popula a partir do JSON
            if (repository.getBooks().isEmpty()) {
                repository.loadBibleFromJson()
            }

            val allBooks = repository.getBooks()
            _books.value = allBooks

            // Carregar Gênesis 1 como exemplo inicial
            if (allBooks.isNotEmpty()) {
                val firstBook = allBooks.first()
                loadVerses(firstBook.id, 1)
            }
        }
    }

    fun loadVerses(bookId: Int, chapterNumber: Int) {
        viewModelScope.launch {
            _verses.value = repository.getVerses(bookId, chapterNumber)
        }
    }
}