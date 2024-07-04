package com.sajeg.arcade

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class ApiClient(private val apiKey: String) {
    private val client = OkHttpClient()

    suspend fun getSession(slackId: String): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/session/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }

    suspend fun getStats(slackId: String): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/stats/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }
}
