package com.prafullkumar.campusepulse.onBoard.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.onBoard.components.SignInField
import com.prafullkumar.campusepulse.onBoard.navigationGraph.Screen
import com.prafullkumar.campusepulse.onBoard.navigationGraph.USER

/**
 *  OnBoardingAdminScreen is a composable function which is used to display the admin login screen.
 *
 * */
@Composable
fun OnBoardingAdminScreen(
    onBoardViewModel: OnBoardViewModel,
    navController: NavController
) {
    val state by onBoardViewModel.state.collectAsState()
    val context = LocalContext.current
    when(state) {
        is OnboardingState.Initial -> {
            // do nothing
        }
        is OnboardingState.Success -> {
            navController.navigate(Screen.ADMIN.route)
        }
        is OnboardingState.Error -> {
            Toast.makeText(
                context,
                (state as OnboardingState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
        is OnboardingState.Loading -> {
            LoadingScreen()
        }
    }
    SignInField(type = USER.ADMIN.name, viewModel = onBoardViewModel) {
        onBoardViewModel.signInUser(
            userType = USER.ADMIN.name,
            email = it.id,
            password = it.pass
        )
    }
}