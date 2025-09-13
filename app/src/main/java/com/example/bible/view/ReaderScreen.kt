package com.example.bible.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.BookPlus
import com.example.bible.viewModel.ReaderViewModel


@Composable
fun ReaderScreen(viewModel: ReaderViewModel) {

    val books by viewModel.books.collectAsState()
    val chapters by viewModel.chapters.collectAsState()
    val verses by viewModel.verses.collectAsState()

    val selectedBook by viewModel.selectedBook.collectAsState()
    val selectedChapter by viewModel.selectedChapter.collectAsState()

    var showBookDialog by remember { mutableStateOf(false) }
    var showChapterDialog by remember { mutableStateOf(false) }


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
        // Header
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = BookPlus,
                        contentDescription = "Book Icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Leitura da Bíblia",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "${selectedBook?.name ?: ""} ${selectedChapter?.number ?: ""}",
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botões de seleção
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FilledTonalButton(
                        onClick = { showBookDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(selectedBook?.name ?: "Selecionar Livro")
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Livros")
                    }

                    FilledTonalButton(
                        onClick = { showChapterDialog = true },
                        modifier = Modifier.weight(1f),
                        enabled = selectedBook != null
                    ) {
                        Text("Capítulo ${selectedChapter?.number ?: "-"}")
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Livros")
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Chapter navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { viewModel.previousChapter() },
                enabled = selectedChapter != null && chapters.indexOf(selectedChapter) > 0
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Anterior")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Anterior")
            }

            Text(
                "${selectedBook?.name ?: ""} ${selectedChapter?.number ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            OutlinedButton(
                onClick = { viewModel.nextChapter() },
                enabled = selectedChapter != null && chapters.indexOf(selectedChapter) < chapters.lastIndex
            ) {
                Text("Próximo")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Filled.ArrowForward, contentDescription = "Próximo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Versículos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            itemsIndexed(verses) { index, verse ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .drawBehind {
                            if (index < verses.lastIndex) {
                                drawLine(
                                    color = Color.LightGray,
                                    start = Offset(0f, size.height),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2f
                                )
                            }
                        },
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${verse.number} ",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = verse.text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }

        // Diálogo de Livros
        if (showBookDialog) {
            AlertDialog(
                onDismissRequest = { showBookDialog = false },
                title = { Text("Selecione o livro") },
                text = {
                    Box(Modifier.height(300.dp).width(300.dp)) {
                        LazyColumn {
                            items(books) { book ->
                                Text(
                                    text = book.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.setBook(book)
                                            showBookDialog = false
                                        }
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {}
            )
        }

        // Diálogo de Capítulos
        if (showChapterDialog) {
            AlertDialog(
                onDismissRequest = { showChapterDialog = false },
                title = { Text("Selecione o capítulo") },
                text = {
                    Box(Modifier.height(300.dp).width(250.dp)) {
                        LazyColumn {
                            items(chapters) { cap ->
                                Text(
                                    text = "Capítulo ${cap.number}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.setChapter(cap)
                                            showChapterDialog = false
                                        }
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                },
                confirmButton = {}
            )
        }
    }
}
