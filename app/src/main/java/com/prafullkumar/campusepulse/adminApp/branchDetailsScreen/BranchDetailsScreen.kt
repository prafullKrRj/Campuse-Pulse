package com.prafullkumar.campusepulse.adminApp.branchDetailsScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun BranchDetailsScreen(viewModel: BranchDetailsViewModel) {
    val branchDetails by viewModel.branchDetails.collectAsState()
    val students by viewModel.studentsFlow.collectAsState()
    when (branchDetails) {
        is BranchDetailsState.Error -> {
            (branchDetails as BranchDetailsState.Error).error?.let { Text(text = it) }
        }
        is BranchDetailsState.Loading -> {
            Text(text = "Loading")
        }
        is BranchDetailsState.Success -> {
            (branchDetails as BranchDetailsState.Success).branchDetails.name?.let { Text(text = it) }
        }

        else -> {}
    }
    when (students) {
        is StudentsState.Error -> {
            (students as StudentsState.Error).error?.let { Text(text = it) }
        }
        is StudentsState.Loading -> {
            Text(text = "Loading")
        }
        is StudentsState.Success -> {
            Text(text = (students as StudentsState.Success).studentList.toString())
        }

        else -> {}
    }
}
