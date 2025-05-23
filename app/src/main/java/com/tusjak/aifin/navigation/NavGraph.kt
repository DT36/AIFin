package com.tusjak.aifin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.tusjak.aifin.data.TransactionCategorizer
import com.tusjak.aifin.ui.viewModels.TransactionViewModel
import com.tusjak.aifin.ui.screens.AddExpensesScreen
import com.tusjak.aifin.ui.screens.AddIncomeScreen
import com.tusjak.aifin.ui.screens.AnalysisScreen
import com.tusjak.aifin.ui.screens.CategoriesScreen
import com.tusjak.aifin.ui.screens.CategoryDetailScreen
import com.tusjak.aifin.ui.screens.HomeScreen
import com.tusjak.aifin.ui.screens.LaunchScreen
import com.tusjak.aifin.ui.screens.TransactionDetailScreen
import com.tusjak.aifin.ui.screens.TransactionsScreen


@Composable
fun NavGraph(
    navController      : NavHostController,
    modifier           : Modifier,
    onGoogleSignInClick: () -> Unit
) {
    val transactionViewModel: TransactionViewModel = viewModel()
    val auth = FirebaseAuth.getInstance()

    NavHost(
        navController    = navController,
        startDestination = NavigationItem.LaunchMenu.route,
        modifier         = modifier
    ) {
        composable(NavigationItem.LaunchMenu.route) { LaunchScreen(onLoginButtonClicked = { onGoogleSignInClick() }) }
        composable(NavigationItem.Home.route) {
            HomeScreen(
                transactions       = transactionViewModel.transactions,
                onTransactionClick = { transactionId ->
                    navController.navigate(NavigationItem.TransactionDetail.createRoute(transactionId))
                },
            )
        }
        composable(NavigationItem.Analysis.route) { AnalysisScreen(viewModel = transactionViewModel) }
        composable(NavigationItem.Transactions.route) {
            TransactionsScreen(
                transactions       = transactionViewModel.transactions,
                onTransactionClick = { transactionId ->
                    navController.navigate(NavigationItem.TransactionDetail.createRoute(transactionId))
                },
            )
        }
        composable(NavigationItem.Categories.route) {
            CategoriesScreen(
                transactions    = transactionViewModel.transactions,
                onCategoryClick = { navController.navigate(NavigationItem.Category.createRoute(it)) })
        }
        composable(
            route     = NavigationItem.Category.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) {
            val categoryId = it.arguments?.getInt("categoryId")

            CategoryDetailScreen(
                categoryId         = categoryId,
                transactions       = transactionViewModel.transactions,
                onTransactionClick = { transactionId ->
                    navController.navigate(
                        NavigationItem.TransactionDetail.createRoute(
                            transactionId
                        )
                    )
                },
            )
        }
        composable(NavigationItem.AddExpenses.route) { AddExpensesScreen(
            onAddExpense = { title, amount, date, category, description, type ->
                transactionViewModel.addTransaction(title, amount, date, category, description, type)
                navController.popBackStack()
            },
            transactionCategorizer = TransactionCategorizer()
        ) }
        composable(NavigationItem.AddIncome.route) { AddIncomeScreen(
            onAddIncome = { title, amount, date, category, description, type ->
                transactionViewModel.addTransaction(title, amount, date, category, description, type)
                navController.popBackStack()
            },
            transactionCategorizer = TransactionCategorizer()
        ) }
        composable(
            route = NavigationItem.TransactionDetail.route,
            arguments = listOf(navArgument("transactionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId")
            val transaction = transactionId?.let { transactionViewModel.getTransactionById(it) }

            TransactionDetailScreen(
                transaction         = transaction,
                onEdit              = { id, title, amount, date, category, description, type ->
                    transactionViewModel.editTransaction(id, title, amount, date, category, description, type)
                    navController.popBackStack()
                },
                onDeleteTransaction = {
                    transactionViewModel.deleteTransaction(it)
                    navController.popBackStack()
                }
            )
        }
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