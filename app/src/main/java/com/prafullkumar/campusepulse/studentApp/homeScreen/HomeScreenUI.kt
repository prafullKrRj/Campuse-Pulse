package com.prafullkumar.campusepulse.studentApp.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.prafullkumar.campusepulse.adminApp.models.Student
import java.util.Calendar

@Composable
fun StudentUI(studentData: Pair<Student, Map<String, List<String>>>) {
    WeekTabRow()
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WeekTabRow() {
    val daysOfWeek = remember { listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday") }
    val selectedTabIndex = remember { mutableIntStateOf(getCurrentDayIndex()) }
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = selectedTabIndex.intValue
    ) {
        daysOfWeek.size
    }


    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = true) { page ->
        Text(text = "Content for ${daysOfWeek[page]}", modifier = Modifier.fillMaxWidth())
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex.intValue = pagerState.currentPage
        state.animateScrollToItem(selectedTabIndex.intValue)
    }
}



/**
 *      Provides the index of the current day of the week
 * */
fun getCurrentDayIndex(): Int {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
    val currentDay = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.FRIDAY -> "Friday"
        else -> "Monday"
    }
    return daysOfWeek.indexOf(currentDay)
}