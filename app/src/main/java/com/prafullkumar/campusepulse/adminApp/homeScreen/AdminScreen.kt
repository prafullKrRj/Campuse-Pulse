package com.prafullkumar.campusepulse.adminApp.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.adminApp.AdminScreens
import com.prafullkumar.campusepulse.adminApp.models.Branch
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
                    (state as AdminState.Success).branches?.let { AdminMainUI(it, navController) }
                }
                is AdminState.Error -> {
                    (state as AdminState.Error).error?.let { Text(text = it) }
                }
                is AdminState.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun AdminMainUI(branches: MutableList<Branch>, navController: NavController) {
    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        items(branches.size) { index ->
            BranchCard(branch = branches[index], navController)
        }
    }
}

@Composable
fun BranchCard(branch: Branch, navController: NavController) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(AdminScreens.BRANCH_DETAILS.name + "/${branch.id}")
            },
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            println(branch)
            if (!branch.name.isNullOrEmpty()) {

                Text(
                    text = "${
                        branch.name.substring(
                            0,
                            branch.name.indexOf('-')
                        ).uppercase()
                    } ${branch.name.substring(branch.name.indexOf('-') + 1)}",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            if (branch.strength != null) {
                Text(text = "Strength: ${branch.strength}", fontSize = 16.sp)
            }
        }
    }
}
