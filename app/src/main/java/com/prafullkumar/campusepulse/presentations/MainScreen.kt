package com.prafullkumar.campusepulse.presentations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navController.navigate("admin")
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Admin")
        }

       /* TextButton(onClick = {
            navController.navigate("teacher")
        }) {
            Text("Teacher")
        }*/
        Button(
            onClick = {
                navController.navigate("student")
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Student")
        }
    }
}