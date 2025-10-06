package com.example.bible.ui.screen.quizzScreen


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bible.ui.screen.readerScreen.ReaderViewModel
import kotlinx.coroutines.launch

@Composable
fun QuizzScreen(
    chapterId: Int,
    quizzViewModel: QuizzViewModel,
    onFinish: (passed: Boolean) -> Unit
) {
    val questions by quizzViewModel.questions.collectAsState()
    val answers = remember { mutableStateListOf<String>() }

    LaunchedEffect(chapterId) {
        quizzViewModel.loadQuestions(chapterId)
    }

    if (questions.isEmpty()) {
        Text("Carregando perguntas...")
        return
    }

    var currentIndex by remember { mutableStateOf(0) }

    val currentQuestion = questions[currentIndex]

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Pergunta ${currentIndex + 1} de ${questions.size}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(currentQuestion.question, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))

        listOf(
            currentQuestion.optionA to "A",
            currentQuestion.optionB to "B",
            currentQuestion.optionC to "C",
            currentQuestion.optionD to "D"
        ).forEach { (text, value) ->
            Button(
                onClick = {
                    if (answers.size > currentIndex) {
                        answers[currentIndex] = value
                    } else {
                        answers.add(value)
                    }
                    if (currentIndex < questions.lastIndex) {
                        currentIndex++
                    } else {
                        val correct = questions.zip(answers).count { it.first.correctAnswer == it.second }
                        onFinish(correct >= 3)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(text)
            }
        }
    }
}