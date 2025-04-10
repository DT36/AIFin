package com.tusjak.aifin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.tusjak.aifin.ui.viewModels.TransactionViewModel
import com.tusjak.aifin.ui.screens.AddExpensesScreen
import com.tusjak.aifin.ui.screens.AddIncomeScreen
import com.tusjak.aifin.ui.screens.AnalysisScreen
import com.tusjak.aifin.ui.screens.HomeScreen
import com.tusjak.aifin.ui.screens.LaunchScreen
import com.tusjak.aifin.ui.screens.TransactionsScreen

@Composable
fun NavGraph(
    navController      : NavHostController,
    modifier           : Modifier,
    onGoogleSignInClick: () -> Unit,
    onSignOutClick     : () -> Unit
) {
    val transactionViewModel: TransactionViewModel = viewModel()
    val auth = FirebaseAuth.getInstance()

    NavHost(
        navController    = navController,
        startDestination = NavigationItem.LaunchMenu.route,
        modifier         = modifier
    ) {
        composable(NavigationItem.LaunchMenu.route) { LaunchScreen(onLoginButtonClicked = { onGoogleSignInClick() }) }
        composable(NavigationItem.Home.route) { HomeScreen(onSignOutClick = onSignOutClick) }
        composable(NavigationItem.Analysis.route) { AnalysisScreen() }
        composable(NavigationItem.Transactions.route) {
            TransactionsScreen(
                transactions     = transactionViewModel.transactions,
                onAddTransaction = { title, amount, category, type ->
                    transactionViewModel.addTransaction(title, amount, category, type)
                },
                onTransactionClick = { transactionId ->
                    navController.navigate("transactionDetail/$transactionId")
                },
                onDeleteTransaction = { transactionId ->
                    transactionViewModel.deleteTransaction(transactionId)
                }
            )
        }
        composable(NavigationItem.Profile.route) {}
        composable(NavigationItem.AddExpenses.route) { AddExpensesScreen() }
        composable(NavigationItem.AddIncome.route) { AddIncomeScreen() }
    }

    // Dynamická kontrola stavu autentifikácie a aktualizácia ViewModelu
    LaunchedEffect(auth.currentUser) {
        transactionViewModel.onAuthStateChanged() // Obnoví listener pri zmene autentifikácie
        if (auth.currentUser != null) {
            navController.navigate(NavigationItem.Home.route) {
                popUpTo(NavigationItem.LaunchMenu.route) { inclusive = true }
                launchSingleTop = true
            }
        } else {
            navController.popBackStack(navController.graph.startDestinationId, true)
            navController.navigate(NavigationItem.LaunchMenu.route) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}