package com.prafullkumar.campusepulse.studentApp.data.repositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.prafullkumar.campusepulse.adminApp.domain.repositories.Result
import com.prafullkumar.campusepulse.studentApp.domain.NoticesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.jsoup.Jsoup


class NoticesRepositoryImpl (
    private val context: Context
) : NoticesRepository {
    override fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getNoticeUrl(link)))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
    override suspend fun getNotices(): Flow<Result<List<String>>> {
        return callbackFlow {
            try {
                trySend(Result.Loading)
                val doc = Jsoup.connect("https://www.aitpune.com/Notices-and-Circulars.aspx/").get()
                val elements = doc.select("h6 a")
                val list = ArrayList<String>()
                for (element in elements) {
                    list.add(element.attr("href"))
                }
                trySend(Result.Success(list))
            } catch (e: Exception) {
                trySend(Result.Error(e))
            }
            awaitClose { close() }
        }
    }
    private fun getNoticeUrl(url: String) : String {
        return "https://www.aitpune.com/${url.replace(" ", "%20")}"
    }
}
