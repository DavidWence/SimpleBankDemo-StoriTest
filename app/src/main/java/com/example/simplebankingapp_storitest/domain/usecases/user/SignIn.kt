package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository

class SignIn(private val usersRepository: UsersRepository) {

    suspend operator fun invoke(email: String, password: String) =
        usersRepository.login(email, password)

}