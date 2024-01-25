package com.prafullkumar.campusepulse.presentations.onBoardingScreen

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
import com.prafullkumar.campusepulse.presentations.navigationGraph.Screen

@Composable
fun OnBoardingScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.ADMIN_SIGNING.route)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Admin")
        }
        Button(
            onClick = {
                navController.navigate(Screen.TEACHER_SIGNING.route)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Teacher")
        }
        Button(
            onClick = {
                navController.navigate(Screen.STUDENT_SIGNING.route)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Student")
        }
    }
}