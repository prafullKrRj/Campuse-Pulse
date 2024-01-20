package com.prafullkumar.campusepulse.adminApp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AdminNavGraph(adminViewModel: AdminViewModel) {
    val adminNavController = rememberNavController()
    NavHost(navController = adminNavController, startDestination = AdminScreens.ADD_STUDENT.name) {
        composable(AdminScreens.HOME.name) {
            AdminScreen(adminViewModel = adminViewModel, navController = adminNavController)
        }
        composable(AdminScreens.ADD_STUDENT.name) {
            AddStudentScreen(adminViewModel = adminViewModel, navController = adminNavController)
        }
        composable(AdminScreens.ADD_BRANCH.name) {
            AddBranchScreen(adminViewModel = adminViewModel, navController = adminNavController)
        }
    }
}
enum class AdminScreens {
    HOME, ADD_STUDENT, ADD_BRANCH
}