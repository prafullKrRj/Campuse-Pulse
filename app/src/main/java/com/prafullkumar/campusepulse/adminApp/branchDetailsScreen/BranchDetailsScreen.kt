package com.prafullkumar.campusepulse.adminApp.branchDetailsScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BranchDetailsScreen(viewModel: BranchDetailsViewModel) {

    Text(text = viewModel.getId())
}