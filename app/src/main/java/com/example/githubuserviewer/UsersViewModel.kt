package com.example.githubuserviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubuserviewer.data.users.User
import com.example.githubuserviewer.data.users.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {

    private val _usersUIState = MutableStateFlow(UsersUIState(isLoading = true))
    val usersUIState: StateFlow<UsersUIState> = _usersUIState.asStateFlow()

    private val _favoriteUsersUIState = MutableStateFlow(FavoriteUsersUIState())
    val favoriteUsersUISTate: StateFlow<FavoriteUsersUIState> = _favoriteUsersUIState.asStateFlow()

    init {
        viewModelScope.launch {
            usersRepository.getUsers()
                .flowOn(Dispatchers.IO)
                .collect {
                    _usersUIState.value = _usersUIState.value.copy(
                        isLoading = false,
                        users = it
                    )
                }
        }
    }

    fun selectUser(index: Int, isSelected: Boolean) {
        val currentUsers = _usersUIState.value.users.toMutableList()
        val currentSelectedUser = currentUsers[index].copy(
            isSelected = isSelected
        )
        currentUsers[index] = currentSelectedUser
        _usersUIState.value = _usersUIState.value.copy(
            users = currentUsers
        )
        if (isSelected) {
            addUserToFavorite(favoriteUser = currentSelectedUser)
        } else {
            removeUserFromFavorite(favoriteUser = currentSelectedUser)
        }

    }

    private fun addUserToFavorite(favoriteUser: User) {
        val currentFavoriteUsers = _favoriteUsersUIState.value.favoriteUsers.toMutableList()
        currentFavoriteUsers.add(favoriteUser.copy(isSelected = false))
        _favoriteUsersUIState.value = _favoriteUsersUIState.value.copy(
            favoriteUsers = currentFavoriteUsers
        )
    }

    private fun removeUserFromFavorite(favoriteUser: User) {
        val currentFavoriteUsers = _favoriteUsersUIState.value.favoriteUsers.toMutableList()
        currentFavoriteUsers.remove(favoriteUser)
        _favoriteUsersUIState.value = _favoriteUsersUIState.value.copy(
            favoriteUsers = currentFavoriteUsers
        )
    }

    companion object {
        fun provideFactory(
            usersRepository: UsersRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UsersViewModel(usersRepository) as T
            }
        }
    }
}

data class UsersUIState (
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
)

data class FavoriteUsersUIState(val favoriteUsers: List<User> = emptyList())
