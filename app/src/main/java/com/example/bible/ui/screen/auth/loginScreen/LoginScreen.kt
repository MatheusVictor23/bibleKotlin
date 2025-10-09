package com.example.bible.ui.screen.auth.loginScreen

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bible.ui.components.CustomSnackBar
import com.example.bible.ui.screen.auth.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    navController: NavController,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var isValidEmail = true
    var enabled = false

    val userSession by authViewModel.userSession.collectAsState()
    val error by authViewModel.error.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarColor by remember { mutableStateOf(Color(0xFF4CAF50)) }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = { CustomSnackBar(snackbarHostState, backgroundColor = snackbarColor, textColor = Color.White, icon = Icons.Filled.Info) }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .fillMaxHeight()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                if(!email.isEmpty() && password.length >= 8){
                    enabled = true
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isValid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    isError = !isValidEmail,
                )
                if(!isValidEmail){
                    Text(
                        text = "Insira um formato válido de email",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("Não possui conta?")

                    TextButton(onClick = {
                        navController.navigate("signup")
                    }) {
                        Text(
                            "Cadastre-se",
                            textDecoration = TextDecoration.Underline,
                            fontSize = 16.sp
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if(isValid) authViewModel.signIn(email, password) else isValidEmail = false
                    },
                    enabled = enabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }

                LaunchedEffect(error, userSession) {
                    when {
                        error != null -> {
                            scope.launch {
                                snackbarColor = Color(0xFFF44336)
                                snackbarHostState.showSnackbar(
                                    message = "Usuário não cadastrado!",
                                    withDismissAction = true,
                                )
                            }
                            authViewModel.resetError()
                        }

                        userSession != null -> {
                            scope.launch {
                                snackbarColor = Color(0xFF4CAF50)
                                snackbarHostState.showSnackbar(
                                    message = "Login realizado com sucesso!",
                                    withDismissAction = true,
                                )

                                navController.navigate("perfil")
                            }
                        }
                    }
                }


        }
    }
}