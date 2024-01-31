package com.prafullkumar.campusepulse.studentApp.domain

import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import kotlinx.coroutines.flow.Flow

interface NoticesRepository {
    fun shareLink(link: String)
    suspend fun getNotices(): Flow<Result<List<String>>>
}
