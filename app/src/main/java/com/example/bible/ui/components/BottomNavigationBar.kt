package com.example.bible.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bible.ui.navigation.Screen
import io.github.jan.supabase.gotrue.user.UserSession

@Composable
fun BottomNavigationBar(
    navController: NavController,
    userSession: UserSession?
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val perfilRoute = if (userSession?.user != null) Screen.Perfil else Screen.SignUp

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Reader,
        Screen.Favorites,
        Screen.Missions,
        perfilRoute
    )

    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    // Evita múltiplas instâncias no stack e restaura estado
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    screen.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = screen.label
                        )
                    }
                },
                label = {
                    screen.label?.let { Text(it) }
                }
            )
        }
    }
}
