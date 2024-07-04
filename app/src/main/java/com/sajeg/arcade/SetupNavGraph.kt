package com.sajeg.arcade

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Setup) {
        composable<HomeScreen> {
            HomeScreen(navController)
        }
        composable<Setup> {
            SetupScreen(navController)
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object Setup