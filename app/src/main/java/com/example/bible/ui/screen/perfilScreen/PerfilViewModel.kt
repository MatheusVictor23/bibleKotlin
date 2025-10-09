package com.example.bible.ui.screen.perfilScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bible.data.remote.model.User
import com.example.bible.data.remote.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel(val userRepository: UserRepository): ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user

    private val _error = MutableStateFlow<String?>(null)

    suspend fun getUser(userEmail:String){
        viewModelScope.launch {
            try {
                _user.value = userRepository.getUser(userEmail)
            }catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

}