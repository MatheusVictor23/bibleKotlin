package com.example.bible.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bible.ui.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        val bottomNavItems = listOf(
            Screen.Home,
            Screen.Reader,
            Screen.Favorites,
            Screen.Missions,
            Screen.Perfil,
        )

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Evita empilhar várias instâncias da mesma tela
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { screen.icon?.let { Icon(it, contentDescription = screen.label) } },
                label = { screen.label?.let { Text(it) } }
            )
        }
    }
}