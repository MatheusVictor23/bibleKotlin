package com.example.bible.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bible.components.BottomNavigationBar
import com.example.bible.view.HomeScreen
import com.example.bible.view.ReaderScreen
import com.example.bible.viewModel.ReaderViewModel


sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Início", Icons.Filled.Build)
    object Reader : Screen("reader", "Leitor", Icons.Filled.Lock)
    object Favorites : Screen("favorites", "Favoritos", Icons.Filled.Star)
    object Missions : Screen("missions", "Missões", Icons.Filled.Person)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Reader,
    Screen.Favorites,
    Screen.Missions
)


@Composable
fun AppNavigation(viewModel: ReaderViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(
                username = "Leitor",
                level = 3,
                progressPercentage = 0.65f,
                pointsToNextLevel = 120,
                totalPoints = 1520,
                streak = 5,
                navController = navController
            ) }
            composable(Screen.Reader.route) { ReaderScreen(viewModel) }
            composable(Screen.Favorites.route) { FavoritesScreen() }
            composable(Screen.Missions.route) { MissionsScreen() }
        }
    }
}

@Composable
fun FavoritesScreen() { Text("❤️ Favoritos") }

@Composable
fun MissionsScreen() { Text("🎯 Missões") }