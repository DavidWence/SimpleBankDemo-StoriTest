package com.example.simplebankingapp_storitest.domain.repositories

import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.user.UserInfo

interface UsersRepository {
    suspend fun signIn(email: String, password: String): Outcome<UserInfo>

}