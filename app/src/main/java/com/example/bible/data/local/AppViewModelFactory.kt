package com.example.bible.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bible.data.local.repository.BibleRepository
import com.example.bible.data.local.repository.QuizzRepository
import com.example.bible.data.remote.repository.AuthRepository
import com.example.bible.ui.screen.quizzScreen.QuizzViewModel
import com.example.bible.ui.screen.readerScreen.ReaderViewModel

class AppViewModelFactory(
    private val readerRepository: BibleRepository,
    private val quizzRepository: QuizzRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ReaderViewModel::class.java) -> {
                ReaderViewModel(readerRepository) as T
            }
            modelClass.isAssignableFrom(QuizzViewModel::class.java) -> {
                QuizzViewModel(quizzRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}


