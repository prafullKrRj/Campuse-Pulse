package com.prafullkumar.campusepulse.adminApp.ui.homeScreen

import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.filled.Info
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.adminApp.AdminScreens
import com.prafullkumar.campusepulse.adminApp.AdminState
import com.prafullkumar.campusepulse.adminApp.AdminViewModel
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.ui.components.AddButton
import com.prafullkumar.campusepulse.commons.TopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel,
    navController: NavController,
    signOut: () -> Unit = { }
) {
    val state by adminViewModel.state.collectAsState()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        adminViewModel.getBranches()
        delay(1500)
        refreshing = false
    }
    val refreshState = rememberPullRefreshState(refreshing, ::refresh)
    var openDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                label = R.string.admin,
                actionIcon = Icons.Default.AccountBox,
                actionIconClicked = {
                    openDialog = true
                }
            )
        },
        floatingActionButton = {
            AddButton(
                addClass = {
                    navController.navigate(AdminScreens.ADD_BRANCH.name)
                }
            ) {
                navController.navigate(AdminScreens.ADD_STUDENT.name)
            }
        },
    ) { paddingValues ->
        Box(modifier = Modifier.pullRefresh(refreshState)) {
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
                        (state as AdminState.Success).branches?.let {
                            AdminMainUI(
                                it,
                                navController
                            )
                        }
                    }

                    is AdminState.Error -> {
                        (state as AdminState.Error).error?.let { Text(text = it) }
                    }

                    is AdminState.Loading -> {
                        CircularProgressIndicator()
                    }
                }

            }
            PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
        }
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
            title = {
                Text(text = "LogOut")
            },
            text = {
                Text("Want to log Out?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                        signOut()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
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
