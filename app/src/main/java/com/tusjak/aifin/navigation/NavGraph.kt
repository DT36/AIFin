package com.tusjak.aifin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tusjak.aifin.ui.screens.AnalysisScreen
import com.tusjak.aifin.ui.screens.HomeScreen
import com.tusjak.aifin.ui.screens.LaunchScreen
import com.tusjak.aifin.ui.screens.TransactionsScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.LaunchMenu.route,
        modifier = modifier
    ) {
        composable(NavigationItem.LaunchMenu.route) {
            LaunchScreen(
                onLoginButtonClicked = { navController.navigate(Screen.HOME.name) }
            )
        }
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Analysis.route) { AnalysisScreen() }
        composable(NavigationItem.Transactions.route) { TransactionsScreen() }
        composable(NavigationItem.Profile.route) { TransactionsScreen() }
    }
}