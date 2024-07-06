package com.sajeg.arcade.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.sajeg.arcade.TokenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ShopScreen() {
    val viewModel = TokenViewModel()
    val context = LocalContext.current
    val shopLink by remember { mutableStateOf(viewModel.getShopLink(context)) }
    // Doesn't work
    val pullToRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()


    AndroidView(
        factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                settings.loadsImagesAutomatically = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                loadUrl(shopLink!!)
            }
        },
        update = { view ->
            if (pullToRefreshState.isRefreshing) {
                coroutineScope.launch {
                    view.reload()
                    pullToRefreshState.endRefresh()
                }
            }
        }
    )
    Column(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullToRefreshContainer(state = pullToRefreshState)
    }
}
