package com.example.fancychat

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseConfig {
    const val URL = "https://kmjnkilposdxzutfgera.supabase.co"
    const val ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imttam5raWxwb3NkeHp1dGZnZXJhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODExMDI2NTMsImV4cCI6MjA5NjY3ODY1M30._r6eUwLTOw9rv6ELTW1j3PpRli-kCvBSuHL93ybO19o"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = URL,
            supabaseKey = ANON_KEY
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
        }
    }
}
