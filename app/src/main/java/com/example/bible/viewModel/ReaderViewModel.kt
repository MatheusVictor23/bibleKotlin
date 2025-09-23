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

    //lista mutavel privada de todos os livros
    /*
        O prefixo _ é uma convenção em Kotlin para indicar que essa variável é de uso interno
        (o ViewModel pode alterar, mas a UI não deve acessar ela diretamente).
    * */
    private val _books = MutableStateFlow<List<BookEntity>>(emptyList())
    //Versao publica e imutavel para a tela observar
    /*
        Aqui você está expondo o fluxo para a UI, mas somente leitura.
        StateFlow não permite alterar o valor, apenas coletar:
    * */
    val books: StateFlow<List<BookEntity>> = _books

    private val _chapters = MutableStateFlow<List<ChapterEntity>>(emptyList())
    val chapters: StateFlow<List<ChapterEntity>> = _chapters

    private val _verses = MutableStateFlow<List<VerseEntity>>(emptyList())
    val verses: StateFlow<List<VerseEntity>> = _verses

    private val _selectedBook = MutableStateFlow<BookEntity?>(null)
    val selectedBook: StateFlow<BookEntity?> = _selectedBook

    private val _selectedChapter = MutableStateFlow<ChapterEntity?>(null)
    val selectedChapter: StateFlow<ChapterEntity?> = _selectedChapter

    //codigo rodado quando o ViewModel e iniciado
    init {
        viewModelScope.launch {
            var allBooks = repository.getBooks()

            if(allBooks.isEmpty()){
                repository.loadBibleFromJson();
                allBooks = repository.getBooks()
            }

            _books.value = allBooks

            if (allBooks.isNotEmpty()) {
                // 🔹 Tenta carregar progresso salvo
                val lecture = repository.getLastLecture()
                if (lecture != null) {
                    val savedBook = allBooks.firstOrNull { it.id == lecture.bookId } ?: allBooks.first()
                    _selectedBook.value = savedBook
                    val bookChapters = repository.getChapters(savedBook.id)
                    _chapters.value = bookChapters

                    val savedChapter = bookChapters.firstOrNull { it.number == lecture.chapterNumber }
                        ?: bookChapters.first()

                    _selectedChapter.value = savedChapter
                    _verses.value = repository.getVerses(savedBook.id, savedChapter.number)
                } else {
                    // 🔹 Se não houver progresso, começa em Gênesis
                    val genesis = allBooks.firstOrNull { it.name.lowercase().contains("gênesis") }
                        ?: allBooks.first()
                    setBook(genesis)
                }
            }
        }
    }

    fun setBook(book: BookEntity) {
        _selectedBook.value = book
        _selectedChapter.value = null
        //inicia uma corrotina para buscar os capitulos do livro
        viewModelScope.launch {
            _chapters.value = repository.getChapters(book.id)
            _verses.value = emptyList() // limpa até escolher capítulo
        }
    }

    fun setChapter(chapter: ChapterEntity) {
        _selectedChapter.value = chapter
        //inicia uma corrotina para buscar os capitulos do livro
        viewModelScope.launch {
            val bookId = _selectedBook.value?.id ?: return@launch
            _verses.value = repository.getVerses(bookId, chapter.number)

            repository.saveLastLecture(bookId, chapter.number)
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