package com.prafullkumar.campusepulse.studentApp.ui.noticeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.studentApp.domain.NoticesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoticeViewModel(
    private val repository: NoticesRepository
) : ViewModel() {

    private val _state = MutableStateFlow<NoticeState>(NoticeState.Loading)
    val state = _state.asStateFlow()
    init {
        getNotices()
    }
    fun getNotices() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNotices().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.value = NoticeState.Loading
                    }
                    is Result.Success -> {
                        _state.value = NoticeState.Success(result.data)
                    }
                    is Result.Error -> {
                        _state.value = NoticeState.Error(result.exception.message ?: "Error")
                    }
                }
            }
        }
    }
    fun shareLink(link: String) {
        repository.shareLink(link)
    }
}
sealed class NoticeState {
    data object Loading : NoticeState()
    data class Success(val data: List<String>) : NoticeState()
    data class Error(val message: String) : NoticeState()
}