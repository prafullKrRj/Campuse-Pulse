package com.prafullkumar.campusepulse.studentApp.ui.attendanceScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.prafullkumar.campusepulse.R
import com.prafullkumar.campusepulse.adminApp.domain.models.Student
import com.prafullkumar.campusepulse.commons.ErrorScreen
import com.prafullkumar.campusepulse.commons.LoadingScreen
import com.prafullkumar.campusepulse.commons.TopAppBar
import com.prafullkumar.campusepulse.studentApp.ui.homeScreen.StudentScreenState
import com.prafullkumar.campusepulse.studentApp.ui.homeScreen.StudentViewModel

@Composable
fun AttendanceScreen(viewModel: StudentViewModel) {
    val state by viewModel.studentScreenState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(label = R.string.attendance)
        }
    ) { paddingValues ->
        when (state) {
            is StudentScreenState.Loading -> {
               LoadingScreen()
            }
            is StudentScreenState.Error -> {
               ErrorScreen {
                     viewModel.getStudentDetails()
               }
            }
            is StudentScreenState.Success -> {
                AttendanceBody(viewModel = viewModel, paddingValues = paddingValues, studentData = (state as StudentScreenState.Success).studentData.first)
            }
            else -> {
                Text(text = "Something went wrong")
            }
        }
    }
}

@Composable
fun AttendanceBody(viewModel: StudentViewModel, paddingValues: PaddingValues, studentData: Student) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OverallAttendanceSection(heading = stringResource(id = R.string.overall_attendance), chartData = GetChartData.getOverallAttendanceData(
            getOverallAttendanceData(studentData.attendance!!)
        )
        )
        studentData.attendance?.forEach { (key, value) ->
            OverallAttendanceSection(heading = key, chartData = GetChartData.getOverallAttendanceData(value))
        }
    }
}

@Composable
fun OverallAttendanceSection(heading: String, chartData: PieChartData, modifier: Modifier = Modifier) {
    val attendance = chartData.slices[0].value/(chartData.slices[0].value + chartData.slices[1].value) * 100
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(.6f)) {
                Text(
                    text = "$heading:  ${attendance}%",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Present: ${chartData.slices[0].value.toInt()} ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Total: ${chartData.slices[1].value.toInt() + chartData.slices[0].value.toInt()}",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
            PieChart(
                modifier = Modifier.weight(.4f),
                pieChartData = chartData,
                pieChartConfig = GetChartData.pieChartConfig()
            )
        }
    }
}
object GetChartData {
    fun pieChartConfig(): PieChartConfig {
        return PieChartConfig(
            labelType = PieChartConfig.LabelType.VALUE,
            isAnimationEnable = true,
            showSliceLabels = true,
            animationDuration = 1500,
            isClickOnSliceEnabled = false
        )
    }
    fun getOverallAttendanceData(attendance: List<Long>): PieChartData {
        val slices = listOf(
            PieChartData.Slice(
                value = attendance[0].toFloat(),
                color = Color.Green,
                label = ""
            ),
            PieChartData.Slice(
                value = attendance[1].toFloat(),
                color = Color.Red,
                label = ""
            ),
        )
        return PieChartData(
            slices = slices,
            plotType = PlotType.Pie,
        )
    }
}
fun getOverallAttendanceData(data: Map<String, List<Long>>): List<Long> {
    var totalP = 0L
    var totalA = 0L
    data.forEach { (key, value) ->
        totalP += value[0]
        totalA += value[1]
    }
    return listOf(totalP, totalA)
}