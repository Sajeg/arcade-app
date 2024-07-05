package com.sajeg.arcade

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = TokenViewModel()
    val context = LocalContext.current
    val slackId by remember { mutableStateOf(viewModel.getSlackId(context)!!) }
    val apiKey by remember { mutableStateOf(viewModel.getApiKey(context)!!) }
    val api = ApiClient(apiKey, slackId)
    var stats by remember { mutableStateOf<JSONObject?>(null) }
    var session by remember { mutableStateOf<JSONObject?>(null) }
    if (stats == null) {
        LaunchedEffect(stats) {
            CoroutineScope(Dispatchers.IO).launch {
                stats = api.getStats()
                Log.d("Stats", stats.toString())
            }
        }
    }
    if (session == null) {
        LaunchedEffect(session) {
            CoroutineScope(Dispatchers.IO).launch {
                session = api.getSession()
                Log.d("Session", session.toString())
            }
        }
    }

    Column(
        modifier = modifierPadding.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (stats != null) {
            if (stats!!.getBoolean("ok")) {
                Text(
                    "You have already",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .offset(y = 20.dp)
                        .padding(20.dp)
                )
                Text(
                    stats!!.getJSONObject("data").getInt("sessions").toString(),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors =
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    )
                )
                Text(
                    "sessions",
                    modifier = Modifier
                        .align(Alignment.End)
                        .offset(y = (-10).dp)
                        .padding(20.dp)
                )
            }
        }
        Text(text = "Api: ${viewModel.getApiKey(LocalContext.current)}")
        Text(text = "slack: ${viewModel.getSlackId(LocalContext.current)}")
    }
}


