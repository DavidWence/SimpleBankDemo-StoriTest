package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.entities.asOutcome
import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository

class GetFullName(private val repository: UserInfoRepository) {

    operator fun invoke() =
        (repository.getName().value + " " + repository.getSurname().value)
            .asOutcome()

}