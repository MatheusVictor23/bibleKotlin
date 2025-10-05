package com.example.bible.database.repository

import android.content.Context
import com.example.bible.database.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserSession

class AuthRepository {
    private val auth = SupabaseClient.client.auth
    private val userRepository = UserRepository()

//    suspend fun signUp(username: String, email: String, password: String): UserSession? {
//        auth.signUpWith(Email) {
//            this.email = email
//            this.password = password
//        }
//
//        val user = auth.currentSessionOrNull()?.user
//
//        if (user != null) {
//            userRepository.createUser(
//                user.id,
//                username,
//                email
//            )
//        }
//
//        return auth.currentSessionOrNull()
//    }

    suspend fun signUp(username: String, email: String, password: String): Pair<UserSession?, Boolean> {
        // Tenta criar a conta
        val session: UserSession? = auth.signUpWith(Email) {
            this.email = email
            this.password = password
        } as UserSession?

        // Usuário atual (pode estar pendente de confirmação)
        val user = auth.currentUserOrNull()

        // Se não houver sessão mas o usuário existir, significa que email de confirmação foi enviado
        val emailSent = session == null && user != null

        // Se a sessão existir, cria perfil no banco
        if (session != null && user != null) {
            userRepository.createUser(user.id, username, email)
        }

        return session to emailSent
    }


    // Login (Sign In)
    suspend fun signIn(email: String, password: String): UserSession? {
        auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        return auth.currentSessionOrNull()
    }

    // Logout
    suspend fun signOut() {
        auth.signOut()
    }

    // Usuário atual
    fun getCurrentUser() = auth.currentUserOrNull()
}
