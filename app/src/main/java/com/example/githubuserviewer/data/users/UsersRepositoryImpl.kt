package com.example.githubuserviewer.data.users

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepositoryImpl : UsersRepository {
    override suspend fun getUsers(): Flow<List<User>> {
        return flow {
            delay(2000)
            emit(USERS)
        }
    }
}