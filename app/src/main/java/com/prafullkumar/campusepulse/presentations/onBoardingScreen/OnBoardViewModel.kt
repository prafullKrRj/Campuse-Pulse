package com.prafullkumar.campusepulse.presentations.onBoardingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.campusepulse.data.OnBoardingRepository
import com.prafullkumar.campusepulse.data.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OnBoardViewModel(
    private val onBoardingRepository: OnBoardingRepository,
): ViewModel() {

    fun isLoggedIn() = onBoardingRepository.isLoggedIn()
    fun loggedInUserType() = onBoardingRepository.loggedInUserType()
    private val _state: MutableStateFlow<OnboardingState> = MutableStateFlow(OnboardingState.Initial)
    val state: StateFlow<OnboardingState> = _state.asStateFlow()
    fun signInUser(userType: String, email: String, password: String) {
        viewModelScope.launch {
            onBoardingRepository.loginToFirebase(email, password).collectLatest {
                when(it) {
                    is Resource.Loading -> {
                        _state.value = OnboardingState.Loading
                    }
                    is Resource.Success -> {
                        onBoardingRepository.setLoggedInUserType(userType)
                        onBoardingRepository.setLoggedIn(it.boolean)
                        _state.value = OnboardingState.Success
                    }
                    is Resource.Error -> {
                        _state.value = OnboardingState.Error(it.message)
                    }
                }
            }
        }
    }
}
sealed interface OnboardingState {
    data object Initial: OnboardingState
    data object Success: OnboardingState
    data class Error(val message: String): OnboardingState
    data object Loading: OnboardingState
}