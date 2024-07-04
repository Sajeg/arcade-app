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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var slackId by remember { mutableStateOf("") }
    val tmpApiKey = getApiKey(LocalContext.current)
    val tmpSlackId = getSlackId(LocalContext.current)

    if (tmpApiKey == null || tmpSlackId == null) {
        navController.navigate(SetupScreen(navController = navController))
    }
    val api = ApiClient("")
    var stats by remember { mutableStateOf("") }

    Column(
        modifier = modifierPadding.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            TextField(
                value = slackId,
                onValueChange = { slackId = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
            Button(onClick = { }) {
                Text(text = "Submit")
            }
    }
}
