package com.example.githubuserviewer.data.users

data class User(
    val id: Long,
    val name: String,
    val avatarId: Int,
    val followers: Int,
    val isSelected: Boolean = false,
)