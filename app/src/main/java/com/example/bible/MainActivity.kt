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
import com.example.bible.roomDatabase.BibleDatabase
import com.example.bible.roomDatabase.repository.BibleRepository
import com.example.bible.navigation.AppNavigation
import com.example.bible.navigation.LinkNavigation
import com.example.bible.ui.theme.BibleTheme
import com.example.bible.view.Auth.LoginScreen
import com.example.bible.viewModel.AuthViewModel
import com.example.bible.viewModel.ReaderViewModel

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
            .fallbackToDestructiveMigration()
            .build()

        val repository = BibleRepository(db.bibleDao(), applicationContext)

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ReaderViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ReaderViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }



        setContent {
            BibleTheme {
               val authViewModel: AuthViewModel = viewModel()
//                val userSession by authViewModel.userSession.collectAsState()
//
//                if (userSession == null) {
//                    LinkNavigation(authViewModel)
//                } else {
//                    val viewModel: ReaderViewModel = viewModel(factory = factory)
//                    AppNavigation(viewModel, authViewModel)
//                }

                val viewModel: ReaderViewModel = viewModel(factory = factory)
                AppNavigation(viewModel, authViewModel)
            }
        }
    }
}