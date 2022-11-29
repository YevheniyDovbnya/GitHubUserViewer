package com.example.githubuserviewer.data.users

import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUsers(): Flow<List<User>>
}