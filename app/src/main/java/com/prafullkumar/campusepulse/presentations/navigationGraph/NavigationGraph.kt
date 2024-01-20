package com.prafullkumar.campusepulse.presentations.navigationGraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.adminApp.AdminNavGraph
import com.prafullkumar.campusepulse.adminApp.AdminScreen
import com.prafullkumar.campusepulse.managers.ViewModelProvider
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardViewModel
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardingAdminScreen
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardingScreen
import com.prafullkumar.campusepulse.presentations.onBoardingScreen.OnBoardingStudentScreen

@Composable
fun NavigationGraph(
    onBoardViewModel: OnBoardViewModel
) {
    val mainNavController = rememberNavController()
    var startDestination = Screen.MAIN.route
    if (onBoardViewModel.isLoggedIn()) {
        startDestination = (
            when (onBoardViewModel.loggedInUserType()) {
                USER.ADMIN.name -> Screen.ADMIN.route
                USER.TEACHER.name -> Screen.TEACHER.route
                USER.STUDENT.name -> Screen.STUDENT.route
                else -> Screen.MAIN.route
            }
        )
    }
    NavHost(navController = mainNavController, startDestination = startDestination) {
        composable(Screen.MAIN.route) {
            OnBoardingScreen(mainNavController)
        }
        composable(Screen.ADMIN_SIGNING.route) {
            OnBoardingAdminScreen(
                navController = mainNavController,
                onBoardViewModel = onBoardViewModel
            )
        }
        composable(Screen.STUDENT_SIGNING.route) {
            OnBoardingStudentScreen(
                navController = mainNavController,
                onBoardViewModel = onBoardViewModel
            )
        }
        composable(Screen.TEACHER_SIGNING.route) {
            Text(text = "teacher")
        }
        composable(Screen.ADMIN.route) {
            AdminNavGraph(adminViewModel = viewModel(
                factory = ViewModelProvider.getAdminViewModel()
            ))
        }
        composable(Screen.STUDENT.route) {
            Text(text = "student")
        }
    }

}
enum class Screen(val route: String) {
    ADMIN_SIGNING("admin_signing"),
    STUDENT_SIGNING("student_signing"),
    TEACHER_SIGNING("teacher"),
    MAIN("main"),
    ADMIN("admin"),
    STUDENT("student"),
    TEACHER("teacher")
}
enum class USER {
    ADMIN, STUDENT, TEACHER
}