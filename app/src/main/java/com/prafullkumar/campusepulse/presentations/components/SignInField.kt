package com.prafullkumar.campusepulse.presentations.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.presentations.navigationGraph.USER


@Composable
fun SignInField(
    type: String,
    onSignIn: (User) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        var identifier by rememberSaveable { mutableStateOf("") }
        var pass by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = identifier,
            onValueChange = { identifier = it },
            label = { Text(
                when (type) {
                    USER.ADMIN.name -> "Admin ID"
                    USER.STUDENT.name -> "Student ID"
                    else -> "ID"
                }
            ) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField {
            pass = it
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (type == USER.ADMIN.name) {
                    onSignIn(User("${identifier}@admin.com", pass))
                } else {
                    onSignIn(User("${identifier}@student.com", pass))
                }
            },
            enabled = identifier.isNotBlank() && pass.isNotBlank()
        ) {
            Text("Login")
        }
    }
}

@Composable
fun PasswordTextField(
    onValueChange: (String) -> Unit = {}
) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onValueChange(it)
        },
        label = { Text("Password") },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = painterResource(if (passwordVisibility) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                    contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                )
            }
        },
    )
}
data class User(val id: String, val pass: String)
