package com.example.simplebankingapp_storitest.domain.repositories

import com.example.simplebankingapp_storitest.domain.entities.Outcome

interface UsersRepository {

    suspend fun login(email: String, password: String): Outcome<Nothing>

}