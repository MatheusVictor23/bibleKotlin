package com.example.bible.ui.screen.perfilScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.BookPlus
import com.composables.Crown
import com.composables.Flame
import com.example.bible.ui.components.icons.Target


@Composable
fun PerfilScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        UserProfileHeader()
        StatsSection()
        AchievementsSection()
        LeaderSection()
        SettingsSection()
    }
}

@Composable
fun UserProfileHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "silassalan@gmail.com",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Nível 1 • 10 pontos",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = 0.1f,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "90 pontos restantes",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
fun StatsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                icon = BookPlus,
                label = "Versículos Lidos",
                value = "0",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = Icons.Default.Favorite,
                label = "Favoritos",
                value = "1",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(
                icon = Flame,
                label = "Sequência",
                value = "0",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = Target,
                label = "Missões",
                value = "0",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, // aqui fica só o modifier normal
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AchievementsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Conquistas",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        AchievementItem(BookPlus, "Primeiro Passo", "Leu o primeiro versículo")
        AchievementItem(Crown, "Explorador", "Leu 10 versículos")
        AchievementItem(Icons.Default.FavoriteBorder, "Colecionador", "Favoritou 5 versículos")
        AchievementItem(Flame, "Sequência de Fogo", "7 dias consecutivos de leitura")
    }
}

@Composable
fun AchievementItem(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun LeaderSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Liderança",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Crown,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Não é líder",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Entre em contato com um administrador para obter acesso",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            SettingItem(
                icon = Icons.Default.Notifications,
                title = "Notificações de Leitura"
            )
            SettingItem(
                icon = Icons.Default.Star,
                title = "Preferências de Tradução"
            )
            SettingItem(
                icon = Icons.Default.Star,
                title = "Backup de Dados"
            )
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* ação futura */ }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}