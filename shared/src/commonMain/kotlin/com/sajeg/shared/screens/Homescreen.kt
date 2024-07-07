package com.sajeg.arcade.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sajeg.arcade.ApiClient
import com.sajeg.arcade.TokenViewModel
import com.sajeg.arcade.modifierPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = TokenViewModel()
    val context = LocalContext.current
    val slackId by remember { mutableStateOf(viewModel.getSlackId(context)!!) }
    val apiKey by remember { mutableStateOf(viewModel.getApiKey(context)!!) }
    val api = ApiClient(apiKey, slackId)
    var stats by remember { mutableStateOf<JSONObject?>(null) }
    var session by remember { mutableStateOf<JSONObject?>(null) }
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            stats = api.getStats()
            session = api.getSession()
            pullToRefreshState.endRefresh()
        }
    }
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

    LazyColumn(
        modifier = modifierPadding
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (stats != null) {
            item {
                DisplayStats(stats = stats!!)
            }
        }
        if (session != null) {
            item {
                if (!session!!.getJSONObject("data").getBoolean("completed")) {
                    DisplaySession(session = session!!)
                }
                DisplayActions(session = session!!, api = api, onPaused = {
                    CoroutineScope(Dispatchers.IO).launch {
                        session = api.getSession()
                        Log.d("Stats", stats.toString())
                    }
                })
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullToRefreshContainer(
            modifier = Modifier,
            state = pullToRefreshState,
        )
    }
}

@Composable
fun DisplayStats(stats: JSONObject) {
    if (!stats.getBoolean("ok")) {
        return
    }
    Text(
        "You have already",
        modifier = Modifier
            .offset(y = 20.dp, x = (-100).dp)
            .padding(20.dp)
    )
    Text(
        stats.getJSONObject("data").getInt("sessions").toString(),
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
            .offset(y = (-10).dp, x = 100.dp)
            .padding(20.dp)
    )
    Text(
        text = "with a total of ",
        modifier = Modifier.offset(y = (-30).dp, x = (-80).dp)
    )
    Text(
        stats.getJSONObject("data").getInt("total").toString(),
        fontSize = 72.sp,
        modifier = Modifier.offset(y = (-20).dp),
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
        "minutes",
        modifier = Modifier
            .offset(x = 50.dp, y = (-40).dp)
            .padding(20.dp)
    )
}

@Composable
fun DisplaySession(session: JSONObject) {
    if (!session.getBoolean("ok")) {
        return
    }
    Text(text = "and", modifier = Modifier.offset(y = (-70).dp, x = (-100).dp))
    Text(
        session.getJSONObject("data").getInt("remaining").toString(),
        fontSize = 64.sp,
        modifier = Modifier.offset(y = (-60).dp),
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors =
                listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.primary
                )
            )
        )
    )
    Text(text = "minutes remaining", modifier = Modifier.offset(y = (-60).dp, x = (-40).dp))
    Text(text = "in your current session", modifier = Modifier.offset(y = (-60).dp, x = 40.dp))
}

@Composable
fun DisplayActions(session: JSONObject, api: ApiClient, onPaused: () -> Unit) {
    if (!session.getJSONObject("data").getBoolean("completed")) {
        Text(text = "What would you like to do?")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { CoroutineScope(Dispatchers.IO).launch { api.pause(); onPaused() } }) {
                Text(
                    text = if (session.getJSONObject("data")
                            .getBoolean("paused")
                    ) "Resume session" else "Pause session"
                )
            }
            Button(onClick = { CoroutineScope(Dispatchers.IO).launch { api.end(); onPaused() } }) {
                Text(text = "End session")
            }
        }
    } else {
        var work by remember { mutableStateOf("") }
        Text(text = "Would you like to start a new session?")
        TextField(
            value = work,
            onValueChange = { work = it },
            placeholder = { Text(text = "What would you like to work on?") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        Button(onClick = { CoroutineScope(Dispatchers.IO).launch { api.start(work); onPaused() } }) {
            Text(text = "Start session")
        }
    }
}