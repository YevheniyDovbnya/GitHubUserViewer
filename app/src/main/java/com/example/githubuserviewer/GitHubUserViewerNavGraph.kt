package com.example.githubuserviewer

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubuserviewer.data.users.UsersRepositoryImpl
import com.example.githubuserviewer.ui.users.favorit.FavoriteUsersScreen
import com.example.githubuserviewer.ui.users.UsersScreen

@Composable
fun GitHubUserViewerNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    val viewModel: UsersViewModel = viewModel(
        factory = UsersViewModel.provideFactory(UsersRepositoryImpl())
    )
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = GitHubUserViewerDestinations.USERS_ROUTE) {
            UsersScreen(
                usersViewModel = viewModel,
                onItemSelected = { index, isSelected ->
                    viewModel.selectUser(
                        index = index,
                        isSelected = isSelected
                    )
                }
            )
        }
        composable(route = GitHubUserViewerDestinations.FAVORITE_USERS_ROUTE) {
            FavoriteUsersScreen(usersViewModel = viewModel)
        }
    }
}