package com.example.githubuserviewer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.githubuserviewer.ui.theme.GitHubUserViewerTheme

@Composable
fun GitHubUserViewerApp() {
    GitHubUserViewerTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            GitHubUserViewerNavigationActions(navController)
        }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination =
            navBackStackEntry?.destination?.route ?: GitHubUserViewerDestinations.USERS_ROUTE
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = when (currentDestination) {
                                GitHubUserViewerDestinations.USERS_ROUTE -> {
                                    stringResource(id = R.string.users_title)
                                }
                                GitHubUserViewerDestinations.FAVORITE_USERS_ROUTE -> {
                                    stringResource(id = R.string.favorite_users_title)
                                }
                                else -> ""
                            }
                        )
                    }
                )
            },
            bottomBar = {
                BottomNavigation {
                    primaryNavigationDestinations.forEach { item ->
                        BottomNavigationItem(
                            selected = currentDestination == item.route,
                            onClick = { navigationActions.navigateTo(item.route) },
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(text = stringResource(id = item.textId)) },
                        )
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding())
            ) {
                GitHubUserViewerNavGraph(
                    navController = navController,
                    startDestination = currentDestination
                )
            }
        }
    }
}