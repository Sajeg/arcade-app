package com.sajeg.arcade

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val SLACK_ID = stringPreferencesKey("slack_id")
fun getApiKey(context: Context) : String? {
    try {
        val slackId: Flow<Any> = context.dataStore.data
            .map { preferences ->
                preferences[SLACK_ID] ?: 0
            }
        return slackId.toString()
    } catch (e: Exception) {
        return null
    }
}

suspend fun setApiKey(context: Context, apiKey: String) {
    context.dataStore.edit { settings ->
        settings[SLACK_ID] = apiKey
    }
}

val API_KEY = stringPreferencesKey("api_key")
fun getSlackId(context: Context) : String? {
    try {
        val apiKey: Flow<Any> = context.dataStore.data
            .map { preferences ->
                preferences[API_KEY] ?: 0
            }
        return apiKey.toString()
    } catch (e: Exception) {
        return null
    }
}

suspend fun setSlackId(slackId: String, context: Context) {
    context.dataStore.edit { settings ->
        settings[API_KEY] = slackId
    }
}