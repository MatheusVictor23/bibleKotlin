package com.example.bible.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bible.viewModel.AuthViewModel

@Composable
fun AuthScreen(authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val userSession by authViewModel.userSession.collectAsState()
    val error by authViewModel.error.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        if (userSession != null) {
            Text("Usuário logado: ${userSession?.user?.email}")
            Button(onClick = { authViewModel.signOut() }) {
                Text("Logout")
            }
        } else {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(onClick = { authViewModel.signIn(email, password) }) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { authViewModel.signUp(email, password) }) {
                    Text("Cadastrar")
                }
            }
        }

        error?.let {
            Text("Erro: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}
