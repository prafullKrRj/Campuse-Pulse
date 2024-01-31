package com.prafullkumar.campusepulse.studentApp.domain

interface AssistantsRepo {
    suspend fun sendMessage(message: String): Response
}


sealed class Response {
    data class Success(val message: String?) : Response()
    data class Error(val message: String) : Response()
}
