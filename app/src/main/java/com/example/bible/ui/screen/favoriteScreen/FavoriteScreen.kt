package com.example.bible.ui.screen.favoriteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.BookPlus
import com.composables.Crown

data class Verses (
    val bookName: String,
    val chapterNumber: Int,
    val number: Int,
    val text: String,
    val isFavorite: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    username: String,
    level: Int,
    progressPercentage: Float,
    pointsToNextLevel: Int,
    totalPoints: Int,
    streak: Int,
) {
    val mockFavorites = listOf(
        Verses(bookName = "Gênesis",  chapterNumber = 1, number = 1, text = "No princípio criou Deus os céus e a terra.", isFavorite = true),
        Verses("Salmos",  chapterNumber = 23, number = 1, text = "O Senhor é o meu pastor; nada me faltará.", isFavorite = true),
        Verses("João",  chapterNumber = 3, number = 16, text = "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito...", isFavorite = true)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                    )
                )
            )
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = BookPlus,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Favoritos", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Bem-vindo, $username", fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Crown
                        Spacer(Modifier.width(4.dp))
                        Text("Nível $level", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(Modifier.height(12.dp))
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Progresso para o próximo nível", fontSize = 12.sp, color = Color.White)
                        Text("$pointsToNextLevel pontos restantes", fontSize = 12.sp, color = Color.White)
                    }
                    LinearProgressIndicator(
                        progress = progressPercentage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .padding(top = 4.dp),
                        color = Color.Yellow
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mockFavorites) { verse ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = "${verse.bookName} ${verse.chapterNumber}:${verse.number}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = verse.text,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}