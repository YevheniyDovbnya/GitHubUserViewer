package com.example.githubuserviewer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

object GitHubUserViewerDestinations {
    const val USERS_ROUTE = "users"
    const val FAVORITE_USERS_ROUTE = "favorite_users"
}

data class PrimaryNavigationDestination(
    val route: String,
    val textId: Int,
    val icon: ImageVector,
)

val primaryNavigationDestinations = listOf(
    PrimaryNavigationDestination(
        route = GitHubUserViewerDestinations.USERS_ROUTE,
        textId = R.string.bottom_navigation_item_list_text,
        icon = Icons.Default.List,
    ),
    PrimaryNavigationDestination(
        route = GitHubUserViewerDestinations.FAVORITE_USERS_ROUTE,
        textId = R.string.bottom_navigation_item_favorite_text,
        icon = Icons.Default.Favorite,
    )
)

class GitHubUserViewerNavigationActions(private val navController: NavController) {
    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}