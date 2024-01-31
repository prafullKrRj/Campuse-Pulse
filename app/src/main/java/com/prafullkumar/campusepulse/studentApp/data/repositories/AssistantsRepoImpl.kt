package com.prafullkumar.campusepulse.studentApp.data.repositories

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.prafullkumar.campusepulse.BuildConfig
import com.prafullkumar.campusepulse.studentApp.domain.AssistantsRepo
import com.prafullkumar.campusepulse.studentApp.domain.Response
import com.prafullkumar.campusepulse.studentApp.ui.assistant.Participant

class AssistantsRepoImpl (

) : AssistantsRepo {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.API_KEY
    )
    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = Participant.USER.role) {
                text(Constants.startingQuery)
            },
            content(role = Participant.MODEL.role) {
                text("Ok")
            }
        )
    )
    override suspend fun sendMessage(message: String): Response {
        return try {
            val response = chat.sendMessage(message)
            Response.Success(response.text)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Unknown error")
        }
    }
}
private object Constants {
    const val startingQuery = "Give the answer max to max in 100 words if you want to increase the limit you can it's your choice and if user specifies that give answer in detail or need more information then you can exceed the limit"
}