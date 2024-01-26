package com.prafullkumar.campusepulse.teacherApp.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.teacherApp.TeacherScreens

@Composable
fun TeacherHomeScreen(viewModel: TeacherViewModel, navController: NavController) {
    val teacherState by viewModel.teacherState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                heading = "Home",
                navIcon = Icons.Default.ArrowBack,
                navIconClicked = {

                }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            item {
                Spacer(modifier = Modifier.width(8.dp))
            }
            item {
                Text(
                    text = "Welcome",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.width(8.dp))
            }
            item {
                Text(
                    text = "Your Classes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.width(8.dp))
            }
            when (teacherState) {
                is TeacherState.Loading -> {
                    item {
                        LoadingScreen()
                    }
                }
                is TeacherState.Success -> {
                    (teacherState as TeacherState.Success).data.branches?.forEachIndexed { index, branch ->

                        item {
                            ClassDetails(
                                branch = branch,
                                index = index + 1
                            ) {
                                navController.navigate(
                                    TeacherScreens.TAKE_ATTENDANCE.name + "/" + branch
                                )
                            }
                        }
                    }
                }
                is TeacherState.Error -> {
                    item {
                        ErrorScreen {
                            viewModel.getClassList()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClassDetails(branch: String, index: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = "Branch: ${branch.substringBefore(":")}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Subject: ${branch.substringAfter(":")}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$index")
        }
    }
}
