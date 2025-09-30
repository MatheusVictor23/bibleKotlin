package com.example.bible.database.repository

import com.example.bible.database.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserSession


class AuthRepository {

    // Cadastro (Sign Up)
    suspend fun signUp(email: String, password: String): UserSession? {
        SupabaseClient.client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        return SupabaseClient.client.auth.currentSessionOrNull()
    }

    // Login (Sign In)
    suspend fun signIn(email: String, password: String): UserSession? {
        SupabaseClient.client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        return SupabaseClient.client.auth.currentSessionOrNull()
    }

    // Logout
    suspend fun signOut() {
        SupabaseClient.client.auth.signOut()
    }

    // Usuário atual
    fun getCurrentUser() = SupabaseClient.client.auth.currentUserOrNull()
}
