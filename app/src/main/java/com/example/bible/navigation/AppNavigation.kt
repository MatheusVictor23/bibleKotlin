package com.example.bible.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composables.BookOpen
import com.composables.Crosshair
import com.composables.House
import com.example.bible.components.BottomNavigationBar
import com.example.bible.view.Auth.LoginScreen
import com.example.bible.view.Auth.SignUpScreen
import com.example.bible.view.FavoritesScreen
import com.example.bible.view.HomeScreen
import com.example.bible.view.Mission
import com.example.bible.view.MissionsScreen
import com.example.bible.view.PerfilScreen
import com.example.bible.view.ReaderScreen
import com.example.bible.viewModel.AuthViewModel
import com.example.bible.viewModel.ReaderViewModel



sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Início", House)
    object Reader : Screen("reader", "Leitor", BookOpen)

    object Missions : Screen("missions", "Missões", Crosshair)

    object Perfil : Screen("perfil", "Perfil", Icons.Filled.Person)

    object Favorites : Screen("favorites", "Favoritos", Icons.Filled.Person)

    object SignUp : Screen("signup", "Cadastrar", Icons.Filled.Person)

    object Login : Screen("login", "Login", Icons.Filled.Person)

}

val missions = listOf(
    Mission(
        id = "m1",
        title = "Primeira Leitura",
        description = "Leia seu primeiro capítulo da Bíblia",
        type = "single",
        targetValue = 1,
        pointsReward = 50,
        completed = true
    ),
    Mission(
        id = "m2",
        title = "Começo de Gênesis",
        description = "Leia os 5 primeiros capítulos de Gênesis",
        type = "chapters",
        targetValue = 5,
        pointsReward = 100,
        completed = false
    ),
    Mission(
        id = "m3",
        title = "Leitura Diária",
        description = "Leia 1 capítulo por dia durante 7 dias consecutivos",
        type = "streak",
        targetValue = 7,
        pointsReward = 200,
        completed = true
    ),
    Mission(
        id = "m4",
        title = "Salmos de Louvor",
        description = "Leia 10 capítulos do livro de Salmos",
        type = "chapters",
        targetValue = 10,
        pointsReward = 150,
        completed = false
    ),
    Mission(
        id = "m5",
        title = "Evangelho em Foco",
        description = "Leia os 4 primeiros capítulos de Mateus",
        type = "chapters",
        targetValue = 4,
        pointsReward = 120,
        completed = true
    ),
    Mission(
        id = "m6",
        title = "Nova Jornada",
        description = "Leia o primeiro capítulo de cada Evangelho (Mateus, Marcos, Lucas e João)",
        type = "multiBook",
        targetValue = 4,
        pointsReward = 250,
        completed = true
    ),
    Mission(
        id = "m7",
        title = "Maratona Semanal",
        description = "Leia 20 capítulos da Bíblia em uma semana",
        type = "chapters",
        targetValue = 20,
        pointsReward = 500,
        completed = false
    ),
    Mission(
        id = "m8",
        title = "Livro Completo",
        description = "Complete a leitura de um livro inteiro da Bíblia",
        type = "book",
        targetValue = 1,
        pointsReward = 800,
        completed = true
    )
)


val bottomNavItems = listOf(
    Screen.Home,
    Screen.Reader,
    Screen.Missions,
    Screen.Perfil,
)


@Composable
fun AppNavigation(readerViewModel: ReaderViewModel, authViewModel: AuthViewModel) {
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
            composable(Screen.Reader.route) { ReaderScreen(readerViewModel) }

            composable(Screen.Missions.route) { MissionsScreen (
                missions,
                false,
                username = "Leitor",
                level = 3,
                progressPercentage = 0.65f,
                pointsToNextLevel = 120,
                totalPoints = 1520,
                streak = 5,)
            }
            composable(Screen.Perfil.route){
                PerfilScreen()

            }

            composable (Screen.Favorites.route){
                FavoritesScreen(
                    username = "Leitor",
                    level = 3,
                    progressPercentage = 0.65f,
                    pointsToNextLevel = 120,
                    totalPoints = 1520,
                    streak = 5,)
            }

        }
    }
}

@Composable
fun LinkNavigation(authViewModel: AuthViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "signup"){
        composable(Screen.Login.route){
            LoginScreen(authViewModel, signUpNavigator = {
                navController.navigate(Screen.SignUp.route, builder = {launchSingleTop = true})
            })
        }

        composable (Screen.SignUp.route){
            SignUpScreen(authViewModel, loginNavigator = {
                navController.navigate(Screen.Login.route, builder = {launchSingleTop = true})
            })
        }
    }
}

