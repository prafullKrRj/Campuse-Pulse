package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.adminApp.uiComponents.InputFieldText

/**
 *  @see com.prafullkumar.campusepulse.adminApp.uiComponents.AddBranchScreen
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBranchScreen(adminViewModel: AdminViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Branch") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                BranchNameRow(adminViewModel)
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(text = "Time Table", fontWeight = SemiBold)
                }
            }
            item {
                AddTimeTableComposable(vm = adminViewModel)
            }
        }
    }
}

@Composable
fun BranchNameRow(vm: AdminViewModel) {
    val newBranch by vm.newBranch.collectAsState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Branch Name", modifier = Modifier.weight(.3f),)
            InputFieldText(
                modifier = Modifier.weight(.7f),
                label = "Branch Name",
                value = vm.branchName.value,
                onValueChange = {
                    vm.branchName.value = it
                })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Year", modifier = Modifier.weight(.3f))
            InputFieldText(
                modifier = Modifier.weight(.7f),
                label = "Branch NameYear",
                value = vm.year.value,
                onValueChange = {
                    vm.year.value = it
                }
            )
        }
}