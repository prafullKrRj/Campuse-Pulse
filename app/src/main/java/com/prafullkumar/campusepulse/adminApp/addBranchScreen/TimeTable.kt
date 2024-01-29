package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.campusepulse.adminApp.AdminScreens
import com.prafullkumar.campusepulse.studentApp.homeScreen.getCurrentDayIndex
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddTimeTable(viewModel: AddBranchViewModel, navController: NavController) {
    val daysOfWeek = remember { listOf("Mon", "Tue", "Wed", "Thur", "Fri") }
    val fullNameDay = remember { listOf("monday", "tuesday", "wednesday", "thursday", "friday") }
    val selectedTabIndex = remember { mutableIntStateOf(getCurrentDayIndex()) }
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = selectedTabIndex.intValue
    ) {
        daysOfWeek.size
    }
    TabRow(
        selectedTabIndex = selectedTabIndex.intValue,
        modifier = Modifier.fillMaxWidth()
    ) {
        daysOfWeek.forEachIndexed { index, text ->
            Tab(
                text = { Text(text, fontSize = 15.sp) },
                selected = selectedTabIndex.intValue == index,
                onClick = {
                    selectedTabIndex.intValue = index
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = true) { page ->
        TimeTableDayComp(viewModel = viewModel, day = fullNameDay[page], navController)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex.intValue = pagerState.currentPage
        state.animateScrollToItem(selectedTabIndex.intValue)
    }
}

@Composable
fun TimeTableDayComp(viewModel: AddBranchViewModel, day: String, navController: NavController) {
    val branchState by viewModel.state.collectAsState()
    Column(modifier = Modifier.heightIn(min = LocalConfiguration.current.screenHeightDp.dp)) {
        branchState.newBranch.timeTable[day]?.forEachIndexed { _, classDetails ->
            Text(text = classDetails.subTeacher.toString())
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (branchState.newBranch.subjects.isNotEmpty() && branchState.newBranch.batches.isNotEmpty()) {
                FilledTonalButton(onClick = { navController.navigate(AdminScreens.ADD_LAB_SLOT_SCREEN.name + "/$day") }) {
                    Text("Add Lab")
                }
                FilledTonalButton(onClick = { navController.navigate(AdminScreens.ADD_THEORY_SLOT_SCREEN.name + "/$day") }) {
                    Text("Add Theory")
                }
            }
        }
    }
}
