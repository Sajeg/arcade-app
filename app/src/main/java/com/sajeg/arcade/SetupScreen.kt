package com.sajeg.arcade

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SetupScreen(navController: NavController) {
    var apiKeyField by remember { mutableStateOf("") }
    var slackIdField by remember { mutableStateOf("") }
    var inputAllowed by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifierPadding.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Enter your ApiKey: ", modifier = Modifier.padding(15.dp))
        TextField(value = apiKeyField, onValueChange = { apiKeyField = it }, enabled = inputAllowed)
        Text(text = "Enter your SlackId: ", modifier = Modifier.padding(15.dp))
        TextField(
            value = slackIdField,
            onValueChange = { slackIdField = it },
            enabled = inputAllowed
        )
        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    inputAllowed = false
                    val client = ApiClient(apiKeyField, slackIdField)
                    val viewModel = TokenViewModel()
                    client.getStats()
                    viewModel.setApiKey(context, apiKeyField)
                    viewModel.setSlackId(context, slackIdField)
                    withContext(Dispatchers.Main) {
                        navController.navigate(HomeScreen)
                    }
                } catch (e: Exception) {
                    inputAllowed = true
                    errorMsg = e.message.toString()
                }
            }
        }, modifier = Modifier.padding(15.dp)) {
            Text(text = "Submit")
        }
        Text(text = errorMsg, style = TextStyle(color = MaterialTheme.colorScheme.error))
    }
}