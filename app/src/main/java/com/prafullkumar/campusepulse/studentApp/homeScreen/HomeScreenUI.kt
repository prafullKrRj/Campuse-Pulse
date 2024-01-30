package com.prafullkumar.campusepulse.studentApp.homeScreen

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.adminApp.models.Student
import java.time.LocalDate
import java.util.Calendar

@Composable
fun StudentUI(studentData: Pair<Student, String>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            CurrentDate()
            Text(text = "${studentData.first.fname}")
        }
        item {
            TimeTableWindow(url = studentData.second)
        }
    }
}
@Composable
fun TimeTableWindow(url: String) {
    Text(text = "Time Table", fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(url).build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        placeholder = painterResource(id = R.drawable.loading_img),
        error = painterResource(id = R.drawable.ic_broken_image)
    )
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