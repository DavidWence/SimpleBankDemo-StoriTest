package com.example.simplebankingapp_storitest.domain.usecases.registry

import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.repositories.RegistrationRepository
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository

class SaveUserInfo(private val usersRepository: UsersRepository,
                   private val registrationRepository: RegistrationRepository) {

    suspend operator fun invoke(name: String, surname: String, email: String, password: String) =
        //primero se verifica que el usuario no exista en el servidor remoto
        when(val verify = usersRepository.validateEmail(email)) {
            is Outcome.Error -> Outcome.Error(verify.description)
            //si el usuario es nuevo se persiste la información de manera local
            else -> {
                registrationRepository.setUserInfo(name, surname, email, password)
                Outcome.Completed
            }
        }

}