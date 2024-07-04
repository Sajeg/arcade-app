package com.sajeg.arcade

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SetupScreen(navController: NavController) {
    var apiKeyField by remember { mutableStateOf("") }
    var slackIdField by remember { mutableStateOf("") }

    Column(
        modifier = modifierPadding.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Enter your ApiKey: ", modifier = Modifier.padding(15.dp))
        TextField(value = apiKeyField, onValueChange = { apiKeyField = it })
        Text(text = "Enter your SlackId: ", modifier = Modifier.padding(15.dp))
        TextField(value = slackIdField, onValueChange = { slackIdField = it })
        Button(onClick = {
            var client = ApiClient(apiKeyField)
        }, modifier = Modifier.padding(15.dp)) {
            Text(text = "Submit")
        }
    }
}