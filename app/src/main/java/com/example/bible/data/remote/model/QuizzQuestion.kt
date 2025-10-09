package com.example.bible.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: String,
    val chapter_id: String,
    val question: String,
    val option_a: String,
    val option_b: String,
    val option_c: String,
    val option_d: String,
    val correct_answer: String
)