package com.example.bible.ui.screen.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bible.data.local.model.FavoriteVerse
import com.example.bible.data.local.repository.FavoriteVerseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoriteVerseRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<Map<String, List<FavoriteVerse>>>(emptyMap())
    val favorites = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getAllFavorites()
        }
    }
}