package com.sajeg.arcade.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sajeg.arcade.ApiClient
import com.sajeg.arcade.TokenViewModel
import com.sajeg.arcade.modifierPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun Sessions() {
    val viewModel = TokenViewModel()
    val api = ApiClient(
        viewModel.getApiKey(LocalContext.current)!!,
        viewModel.getSlackId(LocalContext.current)!!
    )
    var sessions by remember { mutableStateOf<JSONArray?>(null) }

    if (sessions == null) {
        LaunchedEffect(sessions) {
            CoroutineScope(Dispatchers.IO).launch {
                sessions = api.getHistory().getJSONArray("data")
            }
        }
    } else {
        LazyColumn(
            modifier = modifierPadding
        ) {
            for (i in 0..<sessions!!.length()) {
                item {
                    val session = sessions!![i] as JSONObject
                    val (month, day) = getMonthAndDay(session.getString("createdAt"))
                    Card(
                        colors = CardColors(
                            containerColor = if (i % 2 == 0) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = if (i % 2 == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                    ) {
                        Text(
                            text = session.getString("work"),
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "On the $day. $month with the goal: ${session.getString("goal")}",
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                }
            }
        }
    }
}

fun getMonthAndDay(dateString: String): Pair<String, Int> {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val dateTime = LocalDateTime.parse(dateString, formatter)
    val monthName = dateTime.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

    return Pair(monthName, dateTime.dayOfMonth)
}