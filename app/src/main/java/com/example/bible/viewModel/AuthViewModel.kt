    package com.example.bible.viewModel

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.bible.database.repository.AuthRepository
    import io.github.jan.supabase.gotrue.user.UserSession
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch

    class AuthViewModel(
        private val repository: AuthRepository = AuthRepository()
    ) : ViewModel() {

        private val _userSession = MutableStateFlow<UserSession?>(null)
        val userSession: StateFlow<UserSession?> get() = _userSession

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> get() = _error

        fun signUp(email: String, password: String) {
            viewModelScope.launch {
                try {
                    val session = repository.signUp(email, password)
                    _userSession.value = session
                } catch (e: Exception) {
                    _error.value = e.message
                }
            }
        }

        fun signIn(email: String, password: String) {
            viewModelScope.launch {
                try {
                    val session = repository.signIn(email, password)
                    _userSession.value = session
                } catch (e: Exception) {
                    _error.value = e.message
                }
            }
        }


        fun signOut() {
            viewModelScope.launch {
                try {
                    repository.signOut()
                    _userSession.value = null
                } catch (e: Exception) {
                    _error.value = e.message
                }
            }
        }

        fun getCurrentUser() = repository.getCurrentUser()
    }
