package com.tusjak.aifin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tusjak.aifin.navigation.NavGraph
import com.tusjak.aifin.navigation.Screen
import com.tusjak.aifin.theme.AIFinTheme
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.BottomNavigationBar

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            AIFinTheme {
                AIFinApp()
            }
        }
    }
}

@Composable
fun AIFinApp() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar = remember(currentBackStackEntry) {
        currentBackStackEntry?.destination?.route in listOf(
            Screen.HOME.name,
            Screen.ANALYSIS.name,
            Screen.TRANSACTIONS.name,
            Screen.PROFILE.name,
        )
    }

    Scaffold(
        containerColor = background.value,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = { if (showBottomBar) BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}