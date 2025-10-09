package com.example.bible.data.remote.repository


import com.example.bible.data.remote.model.QuizQuestion
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class QuizRepository(private val supabase: SupabaseClient) {

    suspend fun getQuizByChapter(chapterId: String): List<QuizQuestion> {
        return supabase.from("quiz")
            .select()
            .decodeList<QuizQuestion>()
            .filter { it.chapter_id == chapterId }
    }

    suspend fun markChapterAsRead(userId: String, chapterId: String, correct_answers: Int) {
        supabase.from("capitulo_lido").insert(
            mapOf(
                "user_id" to userId,
                "chapter_id" to chapterId,
                "lecture_data" to kotlinx.datetime.Clock.System.now().toString(),
                "correct_answers" to correct_answers,
            )
        )
    }
}