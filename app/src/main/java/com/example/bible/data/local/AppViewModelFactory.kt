package com.example.bible.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bible.data.local.repository.BibleRepository
import com.example.bible.data.local.repository.FavoriteVerseRepository
import com.example.bible.data.local.repository.QuizzRepository
import com.example.bible.data.local.repository.VerseHighlightRepository
import com.example.bible.data.remote.repository.AuthRepository
import com.example.bible.ui.screen.favoriteScreen.FavoriteViewModel
import com.example.bible.ui.screen.quizzScreen.QuizzViewModel
import com.example.bible.ui.screen.readerScreen.ReaderViewModel
import com.example.bible.ui.screen.readerScreen.VerseHighlightViewModel

class AppViewModelFactory(
    private val readerRepository: BibleRepository,
    private val quizzRepository: QuizzRepository,
    private val verseHighlightRepository: VerseHighlightRepository,
    private val favoriteRepository: FavoriteVerseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ReaderViewModel::class.java) -> {
                ReaderViewModel(readerRepository) as T
            }
            modelClass.isAssignableFrom(QuizzViewModel::class.java) -> {
                QuizzViewModel(quizzRepository) as T
            }
            modelClass.isAssignableFrom(VerseHighlightViewModel::class.java) -> {
                VerseHighlightViewModel(verseHighlightRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(favoriteRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}


