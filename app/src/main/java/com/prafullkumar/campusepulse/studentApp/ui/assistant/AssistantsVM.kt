package com.prafullkumar.campusepulse.studentApp.ui.assistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.studentApp.domain.AssistantsRepo
import com.prafullkumar.campusepulse.studentApp.domain.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssistantsViewModel(
    private val repository: AssistantsRepo,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState.Initial)
    val uiState = _uiState.asStateFlow()
    val messages: MutableList<Message> = mutableListOf()
    fun sendMessage(message: String) {
        _uiState.update {
            ChatUiState.Loading
        }
        messages.add(
            0,
            Message(
                text = message,
                person = Participant.USER,
                messageType = MessageType.USER
            )
        )
        viewModelScope.launch {
            when(val response: Response = repository.sendMessage(message)) {
                is Response.Success -> {
                    messages.add(
                        0,
                        Message(
                            text = response.message ?: "No response",
                            person = Participant.MODEL,
                            messageType = MessageType.MODEL
                        )
                    )
                    _uiState.value = ChatUiState.Success
                }
                is Response.Error -> {
                    _uiState.value = ChatUiState.Error(response.message)
                    messages.add(
                        0,
                        Message(
                            text = response.message,
                            person = Participant.MODEL,
                            messageType = MessageType.ERROR
                        )
                    )
                }
            }
        }
    }
}
sealed interface ChatUiState {
    data object Initial : ChatUiState
    data object Loading : ChatUiState
    data object Success : ChatUiState
    data class Error(
        val errorMessage: String
    ) : ChatUiState
}
data class Message(
    val text: String,
    val person: Participant,
    val messageType: MessageType
)
enum class Participant(val role: String) {
    USER("user"),
    MODEL("model")
}
enum class MessageType {
    ERROR, USER, MODEL
}