package com.example.bible.database


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseClient {

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://waitnkubagmwhdkehbuo.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndhaXRua3ViYWdtd2hka2VoYnVvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTczNDU4NjcsImV4cCI6MjA3MjkyMTg2N30.b-H8EvHfSsg5VUlddfxMLW30Nmhy-StHdIpciH79T1s"
    ) {
        install(Postgrest)
        install(Auth)
        install(Realtime)
    }
}