package com.example.githubuserviewer.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubuserviewer.UsersViewModel
import com.example.githubuserviewer.data.users.User
import com.example.githubuserviewer.data.users.UsersRepositoryImpl
import com.example.githubuserviewer.data.users.USERS
import com.example.githubuserviewer.ui.theme.Purple200

@Composable
fun UsersScreen(
    usersViewModel: UsersViewModel,
    onItemSelected: (Int, Boolean) -> Unit
) {
    val usersUiState = usersViewModel.usersUIState.collectAsState()
    if (usersUiState.value.isLoading) {
        LoadingIndicator()
    }
    UsersList(users = usersUiState.value.users, onItemSelected = onItemSelected)
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun UserListItem(
    user: User,
    modifier: Modifier = Modifier,
) {
    Row (modifier = modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = user.avatarId) ,
            contentDescription = null,
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(
                    width = 1.5.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                )
        )
        Spacer(modifier = modifier.width(8.dp))
        Column {
            Text(
                text = user.name,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(
                text = "Followers: ${user.followers}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
fun UsersList(
    users: List<User>,
    modifier: Modifier = Modifier,
    onItemSelected: ((Int, Boolean) -> Any)? = null,
) {
    LazyColumn (
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = users,
            key = { _, item ->  item.id}
        ) { index, item ->
            Card (
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        if (onItemSelected != null) {
                            onItemSelected(index, !item.isSelected)
                        }
                    },
                backgroundColor = if (item.isSelected) {
                    Purple200
                } else {
                    MaterialTheme.colors.surface
                },
                elevation = 4.dp
            ) {
                UserListItem(user = item)
            }
        }
    }
}

@Preview
@Composable
fun PreviewUserListItem() {
    UserListItem(user = USERS[0])
}

@Preview
@Composable
fun PreviewUsersList() {
    UsersList(
        users = USERS,
        onItemSelected = { _, _  -> }
    )
}

@Preview
@Composable
fun PreviewUsersScreen() {
    UsersScreen(
        usersViewModel = UsersViewModel(UsersRepositoryImpl()),
        onItemSelected = { _, _  -> }
    )
}