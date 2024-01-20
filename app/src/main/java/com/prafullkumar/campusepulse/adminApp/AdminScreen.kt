package com.prafullkumar.campusepulse.adminApp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AdminScreen(
    viewModel: AdminViewModel
) {
    Text(text = viewModel.userData.value)
}
