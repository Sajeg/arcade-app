package com.sajeg.arcade.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.sajeg.arcade.ApiClient
import com.sajeg.arcade.TokenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

@Composable
fun Sessions() {
    val viewModel = TokenViewModel()
    val api = ApiClient(
        viewModel.getApiKey(LocalContext.current)!!,
        viewModel.getSlackId(LocalContext.current)!!
    )
    var sessions by remember { mutableStateOf<JSONArray?>(null) }
    LaunchedEffect(sessions) {
        CoroutineScope(Dispatchers.IO).launch {
            sessions = api.getHistory().getJSONArray("data")
        }
    }
    if (sessions != null) {
        LazyColumn {
            for (i in 0..<sessions!!.length()) {
                item {
                    Text(text = sessions!![i].toString())
                }
            }
        }
    }
}