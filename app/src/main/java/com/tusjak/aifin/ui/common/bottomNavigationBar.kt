package com.tusjak.aifin.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tusjak.aifin.common.M
import com.tusjak.aifin.navigation.NavigationItem
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.lightGreen
import com.tusjak.aifin.theme.statePressedAccent
import com.tusjak.aifin.theme.surfaceAccent
import com.tusjak.aifin.theme.surfaceInverted
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.theme.withDisabledOpacity

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute by navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier       = M.clip(RoundedCornerShape(60.dp)),
        containerColor = surfaceAccent.value.withDisabledOpacity()
    ) {
        NavigationItem.navBarScreens.forEach { screen ->
            NavigationBarItem(
                icon     = { Icon(ImageVector.vectorResource(id = screen.icon), contentDescription = stringResource(screen.titleResId)) },
                selected = currentRoute?.destination?.route == screen.route,
                onClick  = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = surfaceInverted.value,
                    unselectedIconColor = surfaceInverted.value,
                    indicatorColor      = statePressedAccent.value)
            )
        }
    }
}