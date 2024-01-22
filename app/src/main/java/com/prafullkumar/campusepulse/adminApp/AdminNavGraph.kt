package com.prafullkumar.campusepulse.adminApp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.campusepulse.adminApp.addBranchScreen.AddBranchScreen
import com.prafullkumar.campusepulse.adminApp.addStudentScreen.AddStudentScreen
import com.prafullkumar.campusepulse.adminApp.branchDetailsScreen.BranchDetailsScreen
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminScreen
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.managers.ViewModelProvider

@Composable
fun AdminNavGraph(adminViewModel: AdminViewModel) {
    val adminNavController = rememberNavController()
    NavHost(navController = adminNavController, startDestination = AdminScreens.HOME.name) {
        composable(AdminScreens.HOME.name) {
            AdminScreen(adminViewModel = adminViewModel, navController = adminNavController)
        }
        composable(AdminScreens.ADD_STUDENT.name) {
            AddStudentScreen(adminViewModel = adminViewModel, navController = adminNavController)
        }
        composable(AdminScreens.ADD_BRANCH.name) {
            AddBranchScreen(adminViewModel = adminViewModel, navController = adminNavController)
        }
        composable(AdminScreens.BRANCH_DETAILS.name + "/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            BranchDetailsScreen(viewModel = viewModel(
                factory = ViewModelProvider.getBranchDetailsViewModel(id = id)
            ))
        }
    }
}
enum class AdminScreens {
    HOME, ADD_STUDENT, ADD_BRANCH, BRANCH_DETAILS
}