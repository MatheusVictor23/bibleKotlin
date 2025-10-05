package com.example.bible.view.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bible.components.CustomSnackBar
import com.example.bible.viewModel.AuthViewModel
import kotlinx.coroutines.launch
import com.example.bible.R

@Composable
fun SignUpScreen(authViewModel: AuthViewModel = viewModel(), loginNavigator: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val emailSent by authViewModel.emailSent.collectAsState()
    var password by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var isValidEmail = true
    var enabled = false

    val userSession by authViewModel.userSession.collectAsState()
    val error by authViewModel.error.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = { CustomSnackBar(snackbarHostState, textColor = Color.Red, icon = Icons.Filled.Info) }
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
            if (userSession != null) {
                Text("Usuário logado: ${userSession?.user?.email}")
                Button(onClick = { authViewModel.signOut() }) {
                    Text("Logout")
                }
            } else {

                if(!email.isEmpty() && password.length >= 8 && username.length >= 3){
                    enabled = true
                }

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = { Text("Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
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

                Text(
                    text = "A senha deve conter no mínimo 8 caracteres, letras maiúsculas, minúsculas, números e caracteres especiais",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("Já possui conta?")

                    TextButton(onClick = { loginNavigator() }) {
                        Text(
                            "Login",
                            textDecoration = TextDecoration.Underline,
                            fontSize = 16.sp
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if(isValid) authViewModel.signUp(username, email, password) else isValidEmail = false
                    },
                    enabled = enabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar-se")
                }

                LaunchedEffect(emailSent) {
                    if (emailSent) {
                        snackbarHostState.showSnackbar("Verifique seu e-mail para confirmar o cadastro!")
                    }
                }

                    error?.let {
                        scope.launch { snackbarHostState.showSnackbar(
                            message = "Erro ao cadastrar usuário. Tente novamente em alguns segundo, se o erro persistir, contate-nos: mrs.thebible.contact@gmail.com",
                            withDismissAction = true,
                        ) }

                        authViewModel.resetError()
                    }
            }
        }
    }
}