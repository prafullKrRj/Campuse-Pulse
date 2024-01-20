package com.prafullkumar.campusepulse.adminApp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.prafullkumar.campusepulse.adminApp.uiComponents.AddButton
import com.prafullkumar.campusepulse.commons.TopAppBar

@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel,
    navController: NavController
) {
    val state by adminViewModel.state.collectAsState()
    Scaffold (
        topBar = {
            TopAppBar(
                label = R.string.admin
            )
        },
        floatingActionButton = {
            AddButton(
                addClass = {
                    navController.navigate(AdminScreens.ADD_BRANCH.name)
                },
                addStudent = {
                    navController.navigate(AdminScreens.ADD_STUDENT.name)
                }
            )
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is AdminState.Initial -> {
                    Text(text = stringResource(R.string.initial))
                }
                is AdminState.Success -> {
                    AdminMainUI((state as AdminState.Success).branches)
                }
                is AdminState.Error -> {
                    Text(text = (state as AdminState.Error).error)
                }
                is AdminState.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun AdminMainUI(branches: MutableList<Branch>) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(branches.size) { index ->
            Text(text = branches[index].name)
        }
    }
}
