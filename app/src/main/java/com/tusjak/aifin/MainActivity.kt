package com.tusjak.aifin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tusjak.aifin.common.M
import com.tusjak.aifin.navigation.NavGraph
import com.tusjak.aifin.navigation.NavigationItem
import com.tusjak.aifin.navigation.Screen
import com.tusjak.aifin.theme.AIFinTheme
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.BottomNavigationBar
import com.tusjak.aifin.ui.common.SpeedDialFAB

class MainActivity : ComponentActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var navController     : NavHostController
    private val auth                       : FirebaseAuth = FirebaseAuth.getInstance()


    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Spracuj chybu
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Konfigurácia Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            AIFinTheme {
                navController = rememberNavController()

                AIFinApp(
                    navController       = navController,
                    onGoogleSignInClick = { signInWithGoogle() },
                    onSignOutClick      = { signOut(navController) }
                )
            }
        }
    }

    // Spustenie Google Sign-In
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    // Autentifikácia s Firebase pomocou Google tokenu
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    navController.navigate(NavigationItem.Home.route) {
                        popUpTo(NavigationItem.LaunchMenu.route) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    // Prihlásenie zlyhalo
                }
            }
    }

    private fun signOut(navController: NavHostController) {
        auth.signOut()

        googleSignInClient.signOut().addOnCompleteListener {
            // Vymaž celý zásobník a presmeruj na LaunchScreen
            navController.popBackStack(navController.graph.startDestinationId, true)
            navController.navigate(NavigationItem.LaunchMenu.route) {
                // Nastav LaunchScreen ako nový root
                popUpTo(0) { inclusive = true } // Vymaže aj štartovaciu destináciu
                launchSingleTop = true
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIFinApp(
    navController      : NavHostController,
    onGoogleSignInClick: () -> Unit,
    onSignOutClick     : () -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar by remember(currentRoute) {
        derivedStateOf {
            currentRoute in listOf(
                Screen.HOME.name,
                Screen.ANALYSIS.name,
                Screen.TRANSACTIONS.name,
                Screen.PROFILE.name,
            )
        }
    }

    val showTopBar by remember(currentRoute) {
        derivedStateOf {
            currentRoute in listOf(
                Screen.ADD_EXPENSES.name,
                Screen.ADD_INCOME.name
            )
        }
    }

    val topBarTitle = when (currentRoute) {
        Screen.ADD_EXPENSES.name -> stringResource(R.string.title_add_expenses)
        Screen.ADD_INCOME.name   -> stringResource(R.string.title_add_income)
        else                     -> ""
    }

    Scaffold(
        containerColor      = Color.Transparent,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar              = {
            AnimatedVisibility(
                visible = showTopBar,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { -it },
                ) + fadeOut()
            ) {
                CenterAlignedTopAppBar(
                    title          = { Text(topBarTitle, color = textColor.value, style = headline4) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                tint = textColor.value,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = mainGreen.value
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomBar) BottomNavigationBar(navController)
        },
        floatingActionButton = {
            if (showBottomBar) {
                SpeedDialFAB(
                    onFirstActionClick = {
                        navController.navigate(NavigationItem.AddIncome.route)
                    },
                    onSecondActionClick = {
                        navController.navigate(NavigationItem.AddExpenses.route)
                    }
                )
            }
        }
    ) { paddingValues ->
        val adjustedModifier = Modifier.padding(
            top   = paddingValues.calculateTopPadding(),
            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
            end   = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
        )

        NavGraph(
            navController       = navController,
            modifier            = adjustedModifier,
            onGoogleSignInClick = onGoogleSignInClick,
            onSignOutClick      = onSignOutClick
        )
    }
}