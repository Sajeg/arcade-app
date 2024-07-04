package com.sajeg.arcade

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sajeg.arcade.ui.theme.ArcadeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
lateinit var modifierPadding: Modifier
lateinit var apiKey: String
lateinit var slackId: String

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArcadeTheme {
                val context = LocalContext.current
                LaunchedEffect(apiKey) {
                    CoroutineScope(Dispatchers.IO).launch {
                        apiKey = getApiKey(context) ?: ""
                    }
                }
                LaunchedEffect(slackId) {
                    CoroutineScope(Dispatchers.IO).launch {
                        slackId = getSlackId(context) ?: ""
                    }
                }
                navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    modifierPadding = Modifier.padding(innerPadding)
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}
