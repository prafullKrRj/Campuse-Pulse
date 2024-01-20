package com.prafullkumar.campusepulse.data

import com.google.firebase.auth.FirebaseAuth

interface AdminRepository {
    suspend fun getUserData(): String
}

class AdminRepositoryImpl (
    private val firebaseAuth: FirebaseAuth
) : AdminRepository {
    override suspend fun getUserData(): String {
        firebaseAuth.currentUser?.let {
            return it.toString()
        }
        return "Hello World"
    }
}