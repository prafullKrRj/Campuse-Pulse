package com.prafullkumar.campusepulse.data

import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

interface OnBoardingRepository {
    suspend fun loginToFirebase(email: String, password: String): Flow<Resource>
    fun isLoggedIn(): Boolean
    fun loggedInUserType(): String
    fun setLoggedIn(boolean: Boolean)
    fun setLoggedInUserType(userType: String)
}

class OnBoardingRepositoryImpl (
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefManager: SharedPrefManager
) : OnBoardingRepository {
    override suspend fun loginToFirebase(email: String, password: String): Flow<Resource> {
        return flow {
            emit(Resource.Loading)
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(result.user != null))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
    override fun isLoggedIn(): Boolean {
        return sharedPrefManager.isLoggedIn()
    }

    override fun loggedInUserType(): String {
        return sharedPrefManager.loggedInUserType()
    }

    override fun setLoggedIn(boolean: Boolean) {
        sharedPrefManager.setLoggedIn(boolean)
    }

    override fun setLoggedInUserType(userType: String) {
        sharedPrefManager.setLoggedInUserType(userType)
    }
}
sealed class Resource {
    data object Loading : Resource()
    data class Success(val boolean: Boolean) : Resource()
    data class Error(val message: String) : Resource()
}