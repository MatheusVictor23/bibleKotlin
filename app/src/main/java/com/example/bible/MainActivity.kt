package com.example.bible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.bible.database.BibleDatabase
import com.example.bible.database.BibleRepository
import com.example.bible.navigation.AppNavigation
import com.example.bible.ui.theme.BibleTheme
import com.example.bible.view.HomeScreen
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
        ).build()

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
                val viewModel: ReaderViewModel = viewModel(factory = factory)

                // 🚀 Chama a navegação
                AppNavigation(viewModel)
            }
        }
    }
}