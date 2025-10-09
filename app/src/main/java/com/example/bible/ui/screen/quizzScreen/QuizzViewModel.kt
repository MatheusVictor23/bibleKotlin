package com.example.bible.ui.screen.quizzScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bible.data.local.entity.QuizzEntity
import com.example.bible.data.local.repository.QuizzRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizzViewModel(private val repository: QuizzRepository) : ViewModel() {

    private val _questions = MutableStateFlow<List<QuizzEntity>>(emptyList())
    val questions: StateFlow<List<QuizzEntity>> get() = _questions

    fun loadQuestions(chapterId: Int) {
        // Mock de perguntas
        val quizzes = listOf(
            QuizzEntity(
                chapterId = chapterId,
                question = "Quem criou os céus e a terra?",
                optionA = "Deus",
                optionB = "Moisés",
                optionC = "Abraão",
                optionD = "Adão",
                correctAnswer = "A"
            ),
            QuizzEntity(
                chapterId = chapterId,
                question = "O que Deus criou no primeiro dia?",
                optionA = "O sol",
                optionB = "A luz",
                optionC = "O homem",
                optionD = "Os animais",
                correctAnswer = "B"
            ),
            QuizzEntity(
                chapterId = chapterId,
                question = "Quantos dias Deus levou para criar tudo?",
                optionA = "3",
                optionB = "5",
                optionC = "6",
                optionD = "7",
                correctAnswer = "C"
            ),
            QuizzEntity(
                chapterId = chapterId,
                question = "O que Deus fez no sétimo dia?",
                optionA = "Criou os animais",
                optionB = "Trabalhou",
                optionC = "Descansou",
                optionD = "Fez o homem",
                correctAnswer = "C"
            )
        )
        _questions.value = quizzes
    }

    fun reset() {
        _questions.value = emptyList()
    }
}