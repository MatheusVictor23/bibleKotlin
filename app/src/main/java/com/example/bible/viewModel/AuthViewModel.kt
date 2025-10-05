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

        private val _emailSent = MutableStateFlow(false)
        val emailSent: StateFlow<Boolean> get() = _emailSent

        fun signUp(username: String, email: String, password: String) {
            viewModelScope.launch {
                try {
                    val (session, emailSent) = repository.signUp(username, email, password)
                    _userSession.value = session
                    _emailSent.value = emailSent
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

        fun resetError() {
            _error.value = null
        }

        fun getCurrentUser() = repository.getCurrentUser()
    }
