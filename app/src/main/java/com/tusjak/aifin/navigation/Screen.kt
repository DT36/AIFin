package com.tusjak.aifin.navigation

import androidx.annotation.StringRes
import com.tusjak.aifin.R

enum class Screen {
    LAUNCH_MENU,
    HOME,
    ANALYSIS,
    TRANSACTIONS,
    PROFILE,
    ADD_EXPENSES,
    ADD_INCOME
}

sealed class NavigationItem(val route: String, @StringRes val titleResId: Int, val icon: Int) {
    data object LaunchMenu   : NavigationItem(Screen.LAUNCH_MENU.name, R.string.title_login_screen, R.drawable.home)
    data object Home         : NavigationItem(Screen.HOME.name, R.string.title_home, R.drawable.home)
    data object Analysis     : NavigationItem(Screen.ANALYSIS.name, R.string.title_analysis, R.drawable.analysis)
    data object Transactions : NavigationItem(Screen.TRANSACTIONS.name, R.string.title_transactions, R.drawable.transactions)
    data object Profile      : NavigationItem(Screen.PROFILE.name, R.string.title_home, R.drawable.category)
    data object AddExpenses  : NavigationItem(Screen.ADD_EXPENSES.name, R.string.title_add_expenses, R.drawable.home)
    data object AddIncome    : NavigationItem(Screen.ADD_INCOME.name, R.string.title_add_income, R.drawable.home)

    companion object {
        val navBarScreens = listOf(Home, Analysis, Transactions, Profile)
    }
}