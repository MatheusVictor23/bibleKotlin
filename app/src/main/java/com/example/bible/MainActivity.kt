package com.example.bible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.bible.data.local.AppViewModelFactory
import com.example.bible.data.local.BibleDatabase
import com.example.bible.data.local.repository.BibleRepository
import com.example.bible.data.local.repository.FavoriteVerseRepository
import com.example.bible.data.local.repository.QuizzRepository
import com.example.bible.data.local.repository.VerseHighlightRepository
import com.example.bible.data.remote.repository.UserRepository
import com.example.bible.ui.navigation.AppNavigation
import com.example.bible.ui.theme.BibleTheme
import com.example.bible.ui.screen.auth.AuthViewModel
import com.example.bible.ui.screen.favoriteScreen.FavoriteViewModel
import com.example.bible.ui.screen.perfilScreen.PerfilViewModel
import com.example.bible.ui.screen.quizzScreen.QuizzViewModel
import com.example.bible.ui.screen.readerScreen.ReaderViewModel
import com.example.bible.ui.screen.readerScreen.VerseHighlightViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Criar banco manualmente
        val db = Room.databaseBuilder(
            applicationContext,
            BibleDatabase::class.java,
            "bible.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()

        class PerfilViewModelFactory(
            private val repository: UserRepository
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
                    return PerfilViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        val userRepository = UserRepository()
        val readerRepository = BibleRepository(db.bibleDao(), applicationContext)
        val quizzRepository = QuizzRepository(db.quizzDao())
        val verseHighlightRepository = VerseHighlightRepository(db.verseHighlightDao())
        val favoriteRepository = FavoriteVerseRepository(db.verseHighlightDao())

        val factory = AppViewModelFactory(readerRepository, quizzRepository, verseHighlightRepository, favoriteRepository)

        setContent {
            BibleTheme {
                val authViewModel: AuthViewModel = viewModel()
                val userSession by authViewModel.userSession.collectAsState()

                val readerViewModel: ReaderViewModel = viewModel(factory = factory)
                val quizzViewModel: QuizzViewModel = viewModel(factory = factory)
                val perfilViewModel: PerfilViewModel = viewModel(factory = PerfilViewModelFactory(userRepository))
                val verseHighlightViewModel: VerseHighlightViewModel = viewModel(factory = factory)
                val favoriteViewModel: FavoriteViewModel = viewModel(factory = factory)
                AppNavigation(readerViewModel, authViewModel, perfilViewModel,quizzViewModel, userSession, verseHighlightViewModel, favoriteViewModel)
            }
        }
    }
}
