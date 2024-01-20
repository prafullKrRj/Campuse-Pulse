package com.prafullkumar.campusepulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.presentations.AdminScreen
import com.prafullkumar.campusepulse.presentations.MainScreen
import com.prafullkumar.campusepulse.presentations.StudentScreen
import com.prafullkumar.campusepulse.ui.theme.CampusePulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefManager = SharedPrefManager(this)

        setContent {
            CampusePulseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val mainNavController = rememberNavController()
                    NavHost(navController = mainNavController, startDestination = Screen.MAIN.route) {
                        composable(Screen.MAIN.route) {
                            MainScreen(mainNavController)
                        }
                        composable(Screen.ADMIN.route) {
                            AdminScreen()
                        }
                        composable(Screen.STUDENT.route) {
                            StudentScreen()
                        }
                    }
                    if (sharedPrefManager.isLoggedIn()) {
                        mainNavController.navigate(
                            when (sharedPrefManager.loggedInUserType()) {
                                "admin" -> Screen.ADMIN.route
                                "teacher" -> Screen.TEACHER.route
                                "student" -> Screen.STUDENT.route
                                else -> Screen.MAIN.route
                            }
                        )
                    }
                }
            }
        }
    }
}

enum class Screen(val route: String) {
    ADMIN("admin"),
    STUDENT("student"),
    TEACHER("teacher"),
    MAIN("main")
}
