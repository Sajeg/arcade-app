package com.sajeg.arcade

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = StartApp) {
        composable<HomeScreen> {
            HomeScreen(navController)
        }
        composable<SetupScreen> {
            SetupScreen(navController)
        }

        composable<StartApp> {
            val viewModel = TokenViewModel()

            if (viewModel.getApiKey(LocalContext.current) == "null" ||
                viewModel.getSlackId(LocalContext.current) == "null"
            ) {
                SetupScreen(navController)
            } else {
                HomeScreen(navController)
            }
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object SetupScreen

@Serializable
object StartApp