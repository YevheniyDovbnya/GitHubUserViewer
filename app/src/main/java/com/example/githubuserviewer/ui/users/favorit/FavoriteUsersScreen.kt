package com.example.githubuserviewer.ui.users.favorit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubuserviewer.UsersViewModel
import com.example.githubuserviewer.data.users.UsersRepositoryImpl
import com.example.githubuserviewer.ui.users.UsersList

@Composable
fun FavoriteUsersScreen(usersViewModel: UsersViewModel) {
    val favoriteUsersUIState = usersViewModel.favoriteUsersUISTate.collectAsState()
    if (favoriteUsersUIState.value.favoriteUsers.isEmpty()) {
        EmptyState()
    }
    UsersList(users = favoriteUsersUIState.value.favoriteUsers)
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "No favorite users!")
    }
}

@Preview
@Composable
fun PreviewFavoriteUsersScreen() {
    FavoriteUsersScreen(UsersViewModel(UsersRepositoryImpl()))
}