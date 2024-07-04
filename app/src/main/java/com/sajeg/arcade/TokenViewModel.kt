package com.sajeg.arcade

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenViewModel : ViewModel() {
    val SLACK_ID = stringPreferencesKey("slack_id")
    val API_KEY = stringPreferencesKey("api_key")

    suspend fun getApiKey(context: Context): String? {
        try {
            val slackId = context.dataStore.data
                .map { settings ->
                    settings[SLACK_ID].toString()
                }.first()
            return slackId
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun setApiKey(context: Context, apiKey: String) {
        context.dataStore.edit { settings ->
            settings[SLACK_ID] = apiKey
        }
    }


    suspend fun getSlackId(context: Context): String? {
        try {
            val apiKey = context.dataStore.data
                .map { settings ->
                    settings[API_KEY].toString()
                }.first()
            return apiKey
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun setSlackId(context: Context, slackId: String) {
        context.dataStore.edit { settings ->
            settings[API_KEY] = slackId
        }
    }
}