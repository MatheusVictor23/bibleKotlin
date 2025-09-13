package com.example.bible.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bible.database.BibleRepository
import com.example.bible.database.BookEntity
import com.example.bible.database.ChapterEntity
import com.example.bible.database.VerseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReaderViewModel(private val repository: BibleRepository): ViewModel() {

    private val _books = MutableStateFlow<List<BookEntity>>(emptyList())
    val books: StateFlow<List<BookEntity>> = _books

    private val _chapters = MutableStateFlow<List<ChapterEntity>>(emptyList())
    val chapters: StateFlow<List<ChapterEntity>> = _chapters

    private val _verses = MutableStateFlow<List<VerseEntity>>(emptyList())
    val verses: StateFlow<List<VerseEntity>> = _verses

    private val _selectedBook = MutableStateFlow<BookEntity?>(null)
    val selectedBook: StateFlow<BookEntity?> = _selectedBook

    private val _selectedChapter = MutableStateFlow<ChapterEntity?>(null)
    val selectedChapter: StateFlow<ChapterEntity?> = _selectedChapter

    init {
        viewModelScope.launch {
            val allBooks = repository.getBooks()
            _books.value = allBooks

            if (allBooks.isNotEmpty()) {
                // Seleciona Gênesis por padrão (id 1 ou nome "Gênesis")
                val genesis = allBooks.firstOrNull { it.name.lowercase().contains("gênesis") }
                    ?: allBooks.first()

                _selectedBook.value = genesis

                // Carrega capítulos do livro selecionado
                val bookChapters = repository.getChapters(genesis.id)
                _chapters.value = bookChapters

                if (bookChapters.isNotEmpty()) {
                    val firstChapter = bookChapters.first()
                    _selectedChapter.value = firstChapter

                    // Carrega versículos do primeiro capítulo
                    val firstVerses = repository.getVerses(genesis.id, firstChapter.number)
                    _verses.value = firstVerses
                }
            }
        }
    }

    fun setBook(book: BookEntity) {
        _selectedBook.value = book
        _selectedChapter.value = null
        viewModelScope.launch {
            _chapters.value = repository.getChapters(book.id)
            _verses.value = emptyList() // limpa até escolher capítulo
        }
    }

    fun setChapter(chapter: ChapterEntity) {
        _selectedChapter.value = chapter
        viewModelScope.launch {
            val bookId = _selectedBook.value?.id ?: return@launch
            _verses.value = repository.getVerses(bookId, chapter.number)
        }
    }

    fun nextChapter() {
        val currentChapters = _chapters.value
        val currentIndex = currentChapters.indexOf(_selectedChapter.value)
        if (currentIndex in 0 until currentChapters.lastIndex) {
            setChapter(currentChapters[currentIndex + 1])
        }
    }

    fun previousChapter() {
        val currentChapters = _chapters.value
        val currentIndex = currentChapters.indexOf(_selectedChapter.value)
        if (currentIndex > 0) {
            setChapter(currentChapters[currentIndex - 1])
        }
    }

}