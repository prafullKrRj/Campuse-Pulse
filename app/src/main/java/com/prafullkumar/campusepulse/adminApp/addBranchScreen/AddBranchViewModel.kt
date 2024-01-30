package com.prafullkumar.campusepulse.adminApp.addBranchScreen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.adminRepos.AdminRepository
import com.prafullkumar.campusepulse.data.adminRepos.Result
import com.prafullkumar.campusepulse.data.adminRepos.Result.Loading
import com.prafullkumar.campusepulse.data.teacherRepos.TeacherDetails
import com.prafullkumar.campusepulse.model.NewBranch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBranchViewModel(
    private val repository: AdminRepository
): ViewModel() {

    val state:MutableStateFlow<AddBranchState> = MutableStateFlow(AddBranchState())

    val upLoaded = MutableStateFlow<Uploaded>(Uploaded.Initial)
    init {
        resetNewBranch()
    }

    fun addBranch() {
        viewModelScope.launch {
            repository.addBranch(state.value.newBranch).collect { ans ->
                when (ans) {
                    is Loading -> {
                        upLoaded.update {
                            Uploaded.Loading
                        }
                    }
                    is Result.Error -> {
                        upLoaded.update {
                            Uploaded.Error(ans.exception.message ?: "Error")
                        }
                    }
                    else -> {
                        upLoaded.update {
                            Uploaded.Success("Branch added successfully")
                        }
                    }
                }
            }
        }
    }
    fun resetNewBranch() {
        state.update {
            AddBranchState()
        }
    }
    fun uploadTimeTable(uri: Uri?) {
        state.update {
            it.copy(
                newBranch = it.newBranch.copy(
                    timeTable = uri
                )
            )
        }
    }
}
data class AddBranchState(
    var newBranch: NewBranch = NewBranch(),
)

sealed class Uploaded {
    data object Initial: Uploaded()
    data object Loading: Uploaded()
    data class Success(val url: String): Uploaded()
    data class Error(val msg: String): Uploaded()
}