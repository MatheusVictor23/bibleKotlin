package com.example.bible.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bible.viewModel.ReaderViewModel


@Composable
fun ReaderScreen(viewModel: ReaderViewModel) {
    val selectedBook = "Gênesis"
    val selectedChapter = 1

    val verses by viewModel.verses.collectAsState()


//    val verses = listOf(
//        "No princípio, criou Deus os céus e a terra.",
//        "E a terra era sem forma e vazia; e havia trevas sobre a face do abismo; e o Espírito de Deus se movia sobre a face das águas.",
//        "E disse Deus: Haja luz; e houve luz.",
//        "E viu Deus que era boa a luz; e fez Deus separação entre a luz e as trevas.",
//        "E Deus chamou à luz Dia; e às trevas chamou Noite. E foi a tarde e a manhã, o dia primeiro.",
//        "No princípio, criou Deus os céus e a terra.",
//        "E a terra era sem forma e vazia; e havia trevas sobre a face do abismo; e o Espírito de Deus se movia sobre a face das águas.",
//        "E disse Deus: Haja luz; e houve luz.",
//        "E viu Deus que era boa a luz; e fez Deus separação entre a luz e as trevas.",
//        "E Deus chamou à luz Dia; e às trevas chamou Noite. E foi a tarde e a manhã, o dia primeiro.",
//        "No princípio, criou Deus os céus e a terra.",
//        "E a terra era sem forma e vazia; e havia trevas sobre a face do abismo; e o Espírito de Deus se movia sobre a face das águas.",
//        "E disse Deus: Haja luz; e houve luz.",
//        "E viu Deus que era boa a luz; e fez Deus separação entre a luz e as trevas.",
//        "E Deus chamou à luz Dia; e às trevas chamou Noite. E foi a tarde e a manhã, o dia primeiro."
//    )

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Book Icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        "Leitura da Bíblia",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        "$selectedBook $selectedChapter",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Book & Chapter Select (mockado)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { /* no-op */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(selectedBook)
                }

                OutlinedButton(
                    onClick = { /* no-op */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Capítulo $selectedChapter")
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
                onClick = { /* no-op */ },
                enabled = selectedChapter > 1
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Anterior")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Anterior")
            }

            Text(
                "$selectedBook $selectedChapter",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            OutlinedButton(
                onClick = { /* no-op */ }
            ) {
                Text("Próximo")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Filled.ArrowBack, contentDescription = "Próximo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Verses
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.spacedBy(0.dp) // sem espaço, pois já terá bordas
//        ) {
//            itemsIndexed(verses) { index, verse ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp) // espaço interno acima e abaixo do texto
//                        .drawBehind {
//                            // Borda inferior (se não for o último)
//                            if (index < verses.lastIndex) {
//                                drawLine(
//                                    color = Color.LightGray,
//                                    start = Offset(0f, size.height),
//                                    end = Offset(size.width, size.height),
//                                    strokeWidth = 2f
//                                )
//                            }
//                        },
//                    verticalAlignment = Alignment.Top
//                ) {
//                    // Número do versículo
//                    Text(
//                        text = "${index + 1} ",
//                        fontWeight = FontWeight.Bold,
//                        style = MaterialTheme.typography.bodyLarge
//                    )
//                    // Texto do versículo
//                    Text(
//                        text = verse,
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.padding(bottom = 4.dp) // distância entre texto e borda
//                    )
//                }
//            }
//
//            // Adiciona um padding no final da lista
//            item {
//                Spacer(modifier = Modifier.height(32.dp))
//            }
//        }


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
    }
}