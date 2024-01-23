package com.prafullkumar.campusepulse.studentApp.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.commons.TopAppBar

@Composable
fun StudentHomeScreen(navController: NavController, viewModel: StudentViewModel) {
    val state by viewModel.studentScreenState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                label = R.string.home,
                actionIcon = Icons.Default.Person,
                actionIconClicked = {
                    navController.navigate(StudentHomeScreens.PROFILE.name)
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when(state) {
                is StudentScreenState.Initial -> {
                    Text(stringResource(R.string.initial))
                }
                is StudentScreenState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is StudentScreenState.Success -> {
                    StudentUI((state as StudentScreenState.Success).studentData)
                }
                is StudentScreenState.Error -> {
                    (state as StudentScreenState.Error).error?.let { Text(text = it) }
                }
            }
        }
    }
}
