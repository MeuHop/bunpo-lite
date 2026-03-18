package com.example.bunpolite.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bunpolite.ui.auth.AuthRoute
import com.example.bunpolite.ui.auth.AuthScreen
import com.example.bunpolite.ui.main.MainLessonRoute
import com.example.bunpolite.ui.main.MainLessonScreen
import com.example.bunpolite.ui.shared.ShowSnackbar
import kotlinx.coroutines.launch

@Composable
fun BunpoLiteNavGraph() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val showSnackbar: ShowSnackbar = {
        scope.launch {
            snackbarHostState.showSnackbar(it.message)
        }
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            HomeDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(destination.labelResId)
                        )
                    },
                    label = { Text(stringResource(destination.labelResId)) },
                    selected = currentDestination?.hasRoute(destination.route::class) == true,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding()
            ) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = MainLessonRoute,
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                composable<MainLessonRoute> { MainLessonScreen(
                    openLessonScreen = { lessonId -> },
                    showSnackbar = showSnackbar
                ) }

                composable<AuthRoute> { AuthScreen(
                    goBack = { navController.popBackStack() },
                    showSnackbar = showSnackbar
                ) }

            }
        }
    }
}