package com.sajeg.arcade

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class TokenViewModel : ViewModel() {
    private val SLACK_ID = stringPreferencesKey("slack_id")
    private val API_KEY = stringPreferencesKey("api_key")

    private var storedSlackId: String? = null
    private var storedApiKey: String? = null

    fun getApiKey(context: Context): String? {
        if (storedApiKey != null) {
            return storedApiKey
        } else {
            try {
                val apiKey = runBlocking {
                    context.dataStore.data
                        .map { settings ->
                            settings[API_KEY].toString()
                        }.first()
                }
                storedApiKey = apiKey
                return apiKey
            } catch (e: Exception) {
                return null
            }
        }
    }

    suspend fun setSlackId(context: Context, slackId: String) {
        context.dataStore.edit { settings ->
            settings[SLACK_ID] = slackId
        }
    }


    fun getSlackId(context: Context): String? {
        if(storedSlackId != null) {
            return storedSlackId
        }
        else {
            try {
                val slackId = runBlocking {
                    context.dataStore.data
                        .map { settings ->
                            settings[SLACK_ID].toString()
                        }.first()
                }
                storedSlackId = slackId
                return slackId
            } catch (e: Exception) {
                return null
            }
        }
    }

    suspend fun setApiKey(context: Context, apiKey: String) {
        context.dataStore.edit { settings ->
            settings[API_KEY] = apiKey
        }
    }
}