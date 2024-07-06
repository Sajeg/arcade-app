package com.sajeg.arcade.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.sajeg.arcade.TokenViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ShopScreen() {
    val viewModel = TokenViewModel()
    val context = LocalContext.current
    val shopLink by remember { mutableStateOf(viewModel.getShopLink(context)) }
    AndroidView(factory = {
        WebView(it).apply {
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            loadUrl(shopLink!!)
        }
    })
}
