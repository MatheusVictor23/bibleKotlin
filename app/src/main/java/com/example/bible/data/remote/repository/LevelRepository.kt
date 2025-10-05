package com.example.bible.data.remote.repository

import com.example.bible.data.remote.SupabaseClient.client
import com.example.bible.data.remote.model.Level
import io.github.jan.supabase.postgrest.from

class LevelRepository {
    suspend fun getNextLevelPoints(level: Int): Int? {
        val existing = client.from("Level")
            .select {
                filter { eq("level", level+1) }
            }
            .decodeAs<Level>()

        if (existing.equals(null)) return null

        return existing.requiredPoints

    }
}