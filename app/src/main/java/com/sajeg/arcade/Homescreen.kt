package com.sajeg.arcade

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier) {
    var slackId by remember { mutableStateOf("") }
    var hasId by remember { mutableStateOf(false) }
    val api = ApiClient("")
    var stats by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!hasId) {
            TextField(
                value = slackId,
                onValueChange = { slackId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
            Button(onClick = { hasId = true }) {
                Text(text = "Submit")
            }
        } else {
            Text(text = stats)
            LaunchedEffect(stats) {
                stats = api.getSession(slackId).get("sessions").toString()
            }
        }
    }
}
