package com.example.bible.data.remote


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseClient {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://amdrrdonipetqbvfvohq.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFtZHJyZG9uaXBldHFidmZ2b2hxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTc1NDU3MTcsImV4cCI6MjA3MzEyMTcxN30.ytszVctUzXkUsRPeRWx1cIbybF5a5ZbxFc4yGn6hIQQ"
    ) {
        install(Postgrest)
        install(Auth)
        install(Realtime)
    }
}