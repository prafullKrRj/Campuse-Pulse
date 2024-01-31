package com.prafullkumar.campusepulse.onBoard.domain.repositories

import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    suspend fun loginToFirebase(email: String, password: String): Flow<Resource>
    fun isLoggedIn(): Boolean
    fun loggedInUserType(): String
    fun setLoggedIn(boolean: Boolean)
    fun setLoggedInUserType(userType: String)
}

sealed class Resource {
    data object Loading : Resource()
    data class Success(val boolean: Boolean) : Resource()
    data class Error(val message: String) : Resource()
}