package com.tusjak.aifin.navigation

import androidx.annotation.StringRes
import com.tusjak.aifin.R

enum class Screen {
    LAUNCH_MENU,
    HOME,
    ANALYSIS,
    TRANSACTIONS,
    CATEGORIES,
    ADD_EXPENSES,
    ADD_INCOME,
    DETAIL,
    CATEGGORY
}

sealed class NavigationItem(val route: String, @StringRes val titleResId: Int, val icon: Int) {
    data object LaunchMenu        : NavigationItem(Screen.LAUNCH_MENU.name, R.string.title_login_screen, R.drawable.home)
    data object Home              : NavigationItem(Screen.HOME.name, R.string.title_home, R.drawable.home)
    data object Analysis          : NavigationItem(Screen.ANALYSIS.name, R.string.title_analysis, R.drawable.analysis)
    data object Transactions      : NavigationItem(Screen.TRANSACTIONS.name, R.string.title_transactions, R.drawable.transactions)
    data object Categories        : NavigationItem(Screen.CATEGORIES.name, R.string.title_categories, R.drawable.category)
    data object AddExpenses       : NavigationItem(Screen.ADD_EXPENSES.name, R.string.title_add_expenses, R.drawable.home)
    data object AddIncome         : NavigationItem(Screen.ADD_INCOME.name, R.string.title_add_income, R.drawable.home)
    data object TransactionDetail : NavigationItem(
        route      = "${Screen.DETAIL.name}/{transactionId}",
        titleResId = R.string.transaction_detail,
        icon       = R.drawable.transactions
    ) {
        fun createRoute(transactionId: String) = "${Screen.DETAIL.name}/$transactionId"
    }
    data object Category         : NavigationItem(
        route      = "${Screen.CATEGGORY.name}/{categoryId}",
        titleResId = R.string.category,
        icon       = R.drawable.home
    ) {
        fun createRoute(categoryId: Int) = "${Screen.CATEGGORY.name}/$categoryId"
    }

    companion object {
        val navBarScreens = listOf(Home, Analysis, Transactions, Categories)
    }
}