package com.sajeg.arcade

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    var tmpApiKey by remember { mutableStateOf("") }
    var tmpSlackId by remember { mutableStateOf("") }
    val context = LocalContext.current
    val api = ApiClient("")
    var stats by remember { mutableStateOf("") }

    Column(
        modifier = modifierPadding.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hi from Main")
        Text(text = "Api: $tmpApiKey")
        Text(text = "slack: $tmpSlackId")
    }
}
