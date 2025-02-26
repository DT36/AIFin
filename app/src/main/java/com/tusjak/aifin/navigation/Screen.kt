package com.tusjak.aifin.navigation

import android.graphics.drawable.Drawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.tusjak.aifin.R

sealed class Screen(val route: String, val title: String, val icon: Int) {
    data object Home : Screen("home", "Home", R.drawable.home)
    data object Search : Screen("search", "Search", R.drawable.home)
    data object Profile : Screen("profile", "Profile", R.drawable.home)

    companion object {
        val allScreens = listOf(Home, Search, Profile)
    }
}