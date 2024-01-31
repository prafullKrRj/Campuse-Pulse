package com.prafullkumar.campusepulse.onBoard.navigationGraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.adminApp.AdminNavGraph
import com.prafullkumar.campusepulse.onBoard.ui.OnBoardTeacher
import com.prafullkumar.campusepulse.onBoard.ui.OnBoardViewModel
import com.prafullkumar.campusepulse.onBoard.ui.OnBoardingAdminScreen
import com.prafullkumar.campusepulse.onBoard.ui.OnBoardingScreen
import com.prafullkumar.campusepulse.onBoard.ui.OnBoardingStudentScreen
import com.prafullkumar.campusepulse.studentApp.StudentNavGraph
import com.prafullkumar.campusepulse.teacherApp.TeacherNavGraph

@Composable
fun NavigationGraph(
    onBoardViewModel: OnBoardViewModel,
    signOut : () -> Unit
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
            OnBoardTeacher(
                navController = mainNavController,
                onBoardViewModel = onBoardViewModel
            )
        }
        composable(Screen.ADMIN.route) {
            AdminNavGraph {
                signOut()
            }
        }
        composable(Screen.STUDENT.route) {
            StudentNavGraph {
                signOut()
            }
        }
        composable(Screen.TEACHER.route) {
            TeacherNavGraph {
                signOut()

            }
        }
    }

}
enum class Screen(val route: String) {
    ADMIN_SIGNING("admin_signing"),
    STUDENT_SIGNING("student_signing"),
    TEACHER_SIGNING("teacher_signing"),
    MAIN("main"),
    ADMIN("admin"),
    STUDENT("student"),
    TEACHER("teacher")
}
enum class USER {
    ADMIN, STUDENT, TEACHER
}