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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bunpolite.ui.auth.AuthRoute
import com.example.bunpolite.ui.auth.AuthScreen
import com.example.bunpolite.ui.main.MainLessonRoute
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
    val currentRoute = navBackStackEntry?.destination?.route

    var currentDestination by rememberSaveable { mutableStateOf(HomeDestinations.Main) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            HomeDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(it.labelResId)
                        )
                    },
                    label = { Text(stringResource(it.labelResId)) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
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
                composable<MainLessonRoute> {  }

                composable<AuthRoute> { AuthScreen(
                    goBack = { navController.popBackStack() },
                    showSnackbar = showSnackbar
                ) }

            }
        }
    }
}