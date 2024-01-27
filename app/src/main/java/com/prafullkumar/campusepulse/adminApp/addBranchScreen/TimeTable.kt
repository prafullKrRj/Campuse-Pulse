package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.material3.Text
import androidx.compose.material3.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.campusepulse.adminApp.homeScreen.AdminViewModel
import com.prafullkumar.campusepulse.studentApp.homeScreen.components.TimeTableUI
import com.prafullkumar.campusepulse.studentApp.homeScreen.getCurrentDayIndex


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddTimeTable(viewModel: AdminViewModel) {
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

    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex.intValue = pagerState.currentPage
        state.animateScrollToItem(selectedTabIndex.intValue)
    }
}