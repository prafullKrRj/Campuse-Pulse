package com.prafullkumar.campusepulse.studentApp.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.campusepulse.adminApp.models.Student
import com.prafullkumar.campusepulse.studentApp.homeScreen.components.TimeTableUI
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@Composable
fun StudentUI(studentData: Pair<Student, Map<String, List<String>>>) {
    CurrentDate()
    WeekTabRow(studentData.second)
}

@Composable
fun CurrentDate(date: LocalDate = getCurrentDate()) {
    // Column for the date
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = date.dayOfWeek.name, fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
        Text(text = "${date.dayOfMonth}, ${getShortMonthName(date.monthValue)} ${date.year}", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekTabRow(timeTable: Map<String, List<String>>) {
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
       TimeTableUI(timeTable = timeTable[fullNameDay[page]] ?: listOf())
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex.intValue = pagerState.currentPage
        state.animateScrollToItem(selectedTabIndex.intValue)
    }
}
fun getCurrentDate(): LocalDate {
    return LocalDate.now()
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

fun getShortMonthName(month: Int): String {
    return when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> "Jan"
    }
}