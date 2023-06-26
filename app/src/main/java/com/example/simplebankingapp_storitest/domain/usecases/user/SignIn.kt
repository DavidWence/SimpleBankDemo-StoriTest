package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.repositories.ConfigurationRepository
import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository

class SignIn(private val usersRepository: UsersRepository,
             private val configurationRepository: ConfigurationRepository,
             private val userInfoRepository: UserInfoRepository) {

    suspend operator fun invoke(email: String, password: String) =
        usersRepository.signIn(email, password).also { result ->
            //si el inicio de sesión es exitoso se persisten los datos del usuario
            if(result is Outcome.Success) {
                configurationRepository.setActiveSession(true)
                userInfoRepository.setUserInfo(result.data)
            }
        }

}