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
import com.example.bible.data.local.repository.QuizzRepository
import com.example.bible.ui.navigation.AppNavigation
import com.example.bible.ui.theme.BibleTheme
import com.example.bible.ui.screen.auth.AuthViewModel
import com.example.bible.ui.screen.quizzScreen.QuizzViewModel
import com.example.bible.ui.screen.readerScreen.ReaderViewModel

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

        val readerRepository = BibleRepository(db.bibleDao(), applicationContext)
        val quizzRepository = QuizzRepository(db.quizzDao())

        val factory = AppViewModelFactory(readerRepository, quizzRepository)

        setContent {
            BibleTheme {
                val authViewModel: AuthViewModel = viewModel()
                val userSession by authViewModel.userSession.collectAsState()

                val readerViewModel: ReaderViewModel = viewModel(factory = factory)
                val quizzViewModel: QuizzViewModel = viewModel(factory = factory)
                AppNavigation(readerViewModel, authViewModel, quizzViewModel, userSession)
            }
        }
    }
}

//                val userSession by authViewModel.userSession.collectAsState()
//
//                if (userSession == null) {
//                    LinkNavigation(authViewModel)
//                } else {
//                    val viewModel: ReaderViewModel = viewModel(factory = factory)
//                    AppNavigation(viewModel, authViewModel)
//                }