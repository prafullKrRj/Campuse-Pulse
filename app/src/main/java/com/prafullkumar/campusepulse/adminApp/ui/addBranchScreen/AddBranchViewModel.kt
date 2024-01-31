package com.prafullkumar.campusepulse.adminApp.ui.addBranchScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.domain.models.Branch
import com.prafullkumar.campusepulse.adminApp.domain.models.NewBranch
import com.prafullkumar.campusepulse.adminApp.domain.repositories.AdminRepository
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result.Loading
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
fun convertToBranch(newBranch: NewBranch): Branch {
    return Branch(
        name = getId(newBranch.year, newBranch.branchName),
        strength = 0,
        subjects = newBranch.subjects.map { it.uppercase() },
        batches = newBranch.batches.map { it.uppercase() },
        id = getId(newBranch.year, newBranch.branchName)
    )
}
fun getId(year: String, name: String): String {
    return when (year[0]) {
        '1' -> "fe-${name.uppercase()}"
        '2' -> "se-${name.uppercase()}"
        '3' -> "te-${name.uppercase()}"
        else -> "be-${name.uppercase()}"
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