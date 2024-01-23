package com.prafullkumar.campusepulse.studentApp.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun StudentHomeScreenNavGraph(viewModel: StudentViewModel) {
    val navController = rememberNavController()
    Column(
        Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = StudentHomeScreens.MAIN.name) {
            composable(StudentHomeScreens.MAIN.name) {
                StudentHomeScreen(navController = navController, viewModel = viewModel)
            }
            composable(StudentHomeScreens.PROFILE.name) {
                ProfileScreen(viewModel, navController)
            }
        }
    }
}

enum class StudentHomeScreens {
    MAIN, PROFILE
}