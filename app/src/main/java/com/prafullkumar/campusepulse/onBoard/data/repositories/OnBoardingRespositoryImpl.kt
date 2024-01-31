package com.prafullkumar.campusepulse.onBoard.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.campusepulse.managers.SharedPrefManager
import com.prafullkumar.campusepulse.onBoard.domain.repositories.OnBoardingRepository
import com.prafullkumar.campusepulse.onBoard.domain.repositories.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await


class OnBoardingRepositoryImpl (
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefManager: SharedPrefManager
) : OnBoardingRepository {
    override suspend fun loginToFirebase(email: String, password: String): Flow<Resource> {
        return flow {
            emit(Resource.Loading)
            try {
                val result = firebaseAuth
                    .signInWithEmailAndPassword(email, password)
                    .await()
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