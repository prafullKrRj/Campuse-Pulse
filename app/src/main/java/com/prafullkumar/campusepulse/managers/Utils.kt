package com.prafullkumar.campusepulse.managers

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.jsoup.Jsoup

object Utils {
    fun shareLink(link: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        context.startActivity(intent)
    }
    fun getNotices(): List<String> {
        val doc = Jsoup.connect("https://www.aitpune.com/Notices-and-Circulars.aspx/").get()
        val elements = doc.select("h6 a")
        val list = ArrayList<String>();
        for (element in elements) {
            list.add(element.attr("href"))
        }
        return list
    }
    val url = "https://www.aitpune.com/${getNotices()[1].replace(" ", "%20")}"
}