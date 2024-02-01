package com.prafullkumar.campusepulse.studentApp.ui.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.commons.TimeTableImage
import com.prafullkumar.campusepulse.studentApp.ui.attendanceScreen.GetChartData
import com.prafullkumar.campusepulse.studentApp.ui.attendanceScreen.OverallAttendanceSection
import com.prafullkumar.campusepulse.studentApp.ui.attendanceScreen.getOverallAttendanceData
import java.time.LocalDate

@Composable
fun StudentUI(studentData: Pair<Student, String>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            CurrentDate()
        }
        item {
            TimeTableWindow(url = studentData.second)
        }
        item {
            Text(
                text = stringResource(id = R.string.overall_attendance),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        item {
            OverallAttendanceSection(
                heading = stringResource(id = R.string.overall_attendance), chartData = GetChartData.getOverallAttendanceData(
                    getOverallAttendanceData(studentData.first.attendance!!)
                )
            )
        }
        studentData.first.attendance?.forEach { (key, value) ->
            item {
                OverallAttendanceSection(
                    heading = key,
                    chartData = GetChartData.getOverallAttendanceData(value)
                )
            }
        }
    }
}
@Composable
fun TimeTableWindow(url: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Time Table", fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
        TimeTableImage(data = url)
    }
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
fun getCurrentDate(): LocalDate {
    return LocalDate.now()
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