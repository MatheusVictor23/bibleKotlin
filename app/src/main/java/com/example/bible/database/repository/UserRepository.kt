package com.example.bible.database.repository

import com.example.bible.database.SupabaseClient
import com.example.bible.database.model.User
import io.github.jan.supabase.postgrest.from

class UserRepository {
    private val client = SupabaseClient.client
    private val levelRepository = LevelRepository()

    suspend fun createUser(userId: String, name: String, email: String) {
        // Verifica se o user já existe
        val existing = client.from("User")
            .select {
                filter { eq("email", email) }
            }
            .decodeList<User>()

        // Se não existe, cria um novo
        if (existing.isEmpty()) {
            val newProfile = User(
                id = userId,
                username = name,
                email = email,
            )

            client.from("User").insert(newProfile)
        }
    }

    suspend fun getUser(userId: String): User? {
        return client.from("User").select {
            filter { eq("id", userId) }
        }.decodeSingleOrNull()
    }

    suspend fun updateUserName(userId: String, name: String) {
        val existing = client.from("User")
            .select {
                filter { eq("id", userId) }
            }
            .decodeList<User>()

        if(!existing.isEmpty()){
            client.from("User").update(
                mapOf(
                    "username" to name
                )
            ) {
                filter { eq("id", userId) }
            }
        }
    }

    suspend fun updateUserPassword(userId: String, password: String) {
        val existing = client.from("User")
            .select {
                filter { eq("id", userId) }
            }
            .decodeList<User>()

        if(!existing.isEmpty()){
            client.from("User").update(
                mapOf(
                    "password" to password
                )
            ) {
                filter { eq("id", userId) }
            }
        }
    }

    suspend fun updateUserPoints(userId: String, gainedPoints: Int) {
        val user = getUser(userId)

        if(user == null) return

        val requiredPointsNextLevel = levelRepository.getNextLevelPoints(user.level)

        if(requiredPointsNextLevel == null) return

        val totalPoints = user.totalPoints + gainedPoints

        val points = user.points + gainedPoints

        if(user.points >= requiredPointsNextLevel){
            client.from("User").update(
                mapOf(
                    "level" to user.level + 1,
                    "totalPoints" to totalPoints,
                    "points" to points-requiredPointsNextLevel
                )
            ) {
                filter { eq("id", userId) }
            }
        }else{
            client.from("User").update(
                mapOf(
                    "totalPoints" to totalPoints,
                    "points" to points
                )
            ) {
                filter { eq("id", userId) }
            }
        }

    }


}

// Atualiza pontos e nível
//    suspend fun updatePoints(userId: String, xpGained: Int) {
//        val profile = getProfile(userId) ?: return
//
//        val totalXp = profile.xp + xpGained
//        val level = (totalXp / 100) + 1 // Exemplo: 100 XP = 1 nível
//
//        client.from("profiles").update(
//            mapOf(
//                "xp" to totalXp,
//                "level" to level,
//                "points" to totalXp // pode ser igual ao XP
//            )
//        ) {
//            filter { eq("id", userId) }
//        }
//    }