package com.prafullkumar.campusepulse.teacherApp.takeAttendanceScreen

import androidx.lifecycle.ViewModel
import com.prafullkumar.campusepulse.data.teacherRepos.TakeAttendanceRepository

class TakeAttendanceViewModel(
    private val repository: TakeAttendanceRepository,
    private val branch: String
) : ViewModel() {

}
sealed class TakeAttendanceState {
    data class Loading(val branch: String) : TakeAttendanceState()
    data class Success(val data: String) : TakeAttendanceState()
    data class Error(val error: String) : TakeAttendanceState()
}