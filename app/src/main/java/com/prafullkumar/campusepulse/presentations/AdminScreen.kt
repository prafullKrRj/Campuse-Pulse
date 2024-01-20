package com.prafullkumar.campusepulse.presentations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminScreen() {

}
@Composable
fun SignInUpPage(
    onSignUp: (User) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var pass by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("UserId") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Pass") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                onSignUp(User("${email}@example.com", pass))
            },
            enabled = email.isNotBlank() && pass.isNotBlank()
        ) {
            Text("Sign Up")
        }
    }
}

data class User(
    val userId: String,
    val pass: String
)