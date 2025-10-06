package com.example.bible.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.bible.ui.components.BottomNavigationBar
import com.example.bible.ui.screen.auth.loginScreen.LoginScreen
import com.example.bible.ui.screen.auth.signUpScreen.SignUpScreen
import com.example.bible.ui.screen.favoriteScreen.FavoritesScreen
import com.example.bible.ui.screen.homeScreen.HomeScreen
import com.example.bible.ui.screen.missionScreen.Mission
import com.example.bible.ui.screen.missionScreen.MissionsScreen
import com.example.bible.ui.screen.perfilScreen.PerfilScreen
import com.example.bible.ui.screen.readerScreen.ReaderScreen
import com.example.bible.ui.screen.auth.AuthViewModel
import com.example.bible.ui.screen.readerScreen.ReaderViewModel
import com.example.bible.R
import com.example.bible.ui.screen.quizzScreen.QuizzViewModel
import io.github.jan.supabase.gotrue.user.UserSession


sealed class Screen(val route: String, val label: String?, val icon: ImageVector?) {
    object Home : Screen("home", "Início", House)
    object Reader : Screen("reader", "Leitor", BookOpen)

    object Missions : Screen("missions", "Missões", Crosshair)

    object Perfil : Screen("perfil", "Perfil", Icons.Filled.Person)

    object Favorites : Screen("favorites", "Favoritos", Icons.Filled.FavoriteBorder)

    object SignUp : Screen("signup", "Perfil", Icons.Filled.Person  )

    object Login : Screen("login", null, null)

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


@Composable
fun AppNavigation(readerViewModel: ReaderViewModel, authViewModel: AuthViewModel, quizzViewModel: QuizzViewModel, userSession: UserSession?) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, userSession)
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
            composable(Screen.Reader.route) { ReaderScreen(readerViewModel, quizzViewModel) }

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
                PerfilScreen(authViewModel, navController)
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

            composable(Screen.Login.route){
                LoginScreen(authViewModel, navController)
            }

            composable (Screen.SignUp.route){
                SignUpScreen(
                    authViewModel,
                    navController
                    )
            }

        }
    }
}

//@Composable
//fun LinkNavigation(authViewModel: AuthViewModel){
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "signup"){
//        composable(Screen.Login.route){
//            LoginScreen(authViewModel, signUpNavigator = {
//                navController.navigate(Screen.SignUp.route, builder = {launchSingleTop = true})
//            })
//        }
//
//        composable (Screen.SignUp.route){
//            SignUpScreen(authViewModel, loginNavigator = {
//                navController.navigate(Screen.Login.route, builder = {launchSingleTop = true})
//            })
//        }
//    }
//}

