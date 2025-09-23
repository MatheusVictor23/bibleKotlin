package com.example.bible.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.BookPlus
import com.composables.Crown

data class Mission(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val targetValue: Int,
    val pointsReward: Int,
    val progress: Int = 0,
    val completed: Boolean = false,
    val completedAt: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionsScreen(
    missions: List<Mission> = emptyList(),
    isLoading: Boolean = false,
    username: String,
    level: Int,
    progressPercentage: Float,
    pointsToNextLevel: Int,
    totalPoints: Int,
    streak: Int,
) {
    val completedMissions = missions.filter { it.completed }
    val activeMissions = missions.filter { !it.completed }
    val totalPointsEarned = completedMissions.sumOf { it.pointsReward }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Carregando missões...",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        return
    }

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
                            Text("Missões", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
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

        // Missions List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp) // Space for bottom navigation
        ) {
            // Active Missions Section
            if (activeMissions.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Missões Pendentes"
                    )
                }
                items(activeMissions) { mission ->
                    MissionCard(
                        mission = mission,
                        isCompleted = false
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }
            }

            // Completed Missions Section
            if (completedMissions.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Missões Concluídas",
                    )
                }
                items(completedMissions) { mission ->
                    MissionCard(
                        mission = mission,
                        isCompleted = true
                    )
                }
            }

            // Empty State
            if (missions.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                //flag
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Nenhuma missão disponível",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "As missões aparecerão aqui conforme você progride na leitura.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    iconTint: Color = MaterialTheme.colorScheme.onPrimary
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
            fontSize = 10.sp
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MissionCard(
    mission: Mission,
    isCompleted: Boolean
) {
    val borderColor = if (isCompleted) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
    val backgroundColor = if (isCompleted) {
        Color(0xFF4CAF50).copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = mission.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (isCompleted) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface
                    )
                }

                AssistChip(
                    onClick = { },
                    label = {
                        Text(
                            text = "+${mission.pointsReward} pts",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (isCompleted) {
                            Color(0xFF4CAF50).copy(alpha = 0.2f)
                        } else {
                            Color(0xFFFFD700).copy(alpha = 0.2f)
                        },
                        labelColor = if (isCompleted) Color(0xFF2E7D32) else Color(0xFFB8860B)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = mission.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            if (!isCompleted) {
                Spacer(modifier = Modifier.height(12.dp))

                // Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progresso",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "${mission.progress} / ${mission.targetValue}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = { (mission.progress.toFloat() / mission.targetValue.toFloat()).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )

                // Mission Type Badge
                if (mission.type == "daily") {
                    Spacer(modifier = Modifier.height(8.dp))
                    AssistChip(
                        onClick = { },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    //Schedule
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Diária",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                        )
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                AssistChip(
                    onClick = { },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Concluída",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.2f),
                        labelColor = Color(0xFF2E7D32)
                    )
                )
            }
        }
    }
}

private fun getMissionIcon(mission: Mission): ImageVector {
    return when {
        //Schedule
        mission.type == "daily" -> Icons.Default.AddCircle
        //fire
        mission.title.contains("Sequência", ignoreCase = true) -> Icons.Default.AddCircle
        //book
        mission.title.contains("capítulo", ignoreCase = true) -> Icons.Default.AddCircle
        mission.title.contains("Favorite", ignoreCase = true) ||
                mission.title.contains("Colecionador", ignoreCase = true) -> Icons.Default.Star
        else -> Icons.Default.AddCircle
    }
}