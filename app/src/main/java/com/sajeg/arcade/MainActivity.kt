package com.sajeg.arcade

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sajeg.arcade.ui.theme.ArcadeTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
lateinit var modifierPadding: Modifier

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArcadeTheme {
                navController = rememberNavController()
                Scaffold(bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val viewModel = TokenViewModel()
                    if (viewModel.getApiKey(this) != "null") {
                        NavigationBar {
                            NavigationBarItem(
                                selected = if (currentDestination != null) currentDestination.route == "com.sajeg.arcade.HomeScreen" || currentDestination.route == "com.sajeg.arcade.StartApp" else false,
                                onClick = { navController.navigate(HomeScreen) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.home),
                                        contentDescription = ""
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = if (currentDestination != null) currentDestination.route == "com.sajeg.arcade.ShopScreen" else false,
                                onClick = { navController.navigate(ShopScreen) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.shop),
                                        contentDescription = ""
                                    )
                                }
                            )
                            NavigationBarItem(
                                selected = if (currentDestination != null) currentDestination.route == "com.sajeg.arcade.SessionScreen" else false,
                                onClick = { navController.navigate(SessionScreen) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.alarm),
                                        contentDescription = ""
                                    )
                                }
                            )
                        }
                    }
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    modifierPadding = Modifier.padding(innerPadding)
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}
