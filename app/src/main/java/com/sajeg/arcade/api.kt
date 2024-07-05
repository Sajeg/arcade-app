package com.sajeg.arcade

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class ApiClient(private val apiKey: String, private val slackId: String) {
    private val client = OkHttpClient()

    suspend fun getSession(): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/session/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .build()

        val response = client.newCall(request).execute()

        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }

    suspend fun getStats(): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/stats/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .build()

        val response = client.newCall(request).execute()
        val code = response.code
        if (code == 404) {
            throw Exception("Invalid Credentials")
        }
        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }

    suspend fun pause(): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/pause/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .post(FormBody.Builder().build())
            .build()

        val response = client.newCall(request).execute()
        val code = response.code
        if (code == 404) {
            throw Exception("Invalid Credentials")
        }
        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }

    suspend fun end(): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/cancel/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .post(FormBody.Builder().build())
            .build()

        val response = client.newCall(request).execute()
        val code = response.code
        if (code == 404) {
            throw Exception("Invalid Credentials")
        }
        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }

    suspend fun start(work: String): JSONObject = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://hackhour.hackclub.com/api/start/:$slackId")
            .header("Authorization", "Bearer $apiKey")
            .post(FormBody.Builder().add("work", work).build())
            .build()

        val response = client.newCall(request).execute()
        val code = response.code
        if (code == 404) {
            throw Exception("Invalid Credentials")
        }
        val responseBody = response.body?.string() ?: throw Exception("Empty response body")
        JSONObject(responseBody)
    }
}
