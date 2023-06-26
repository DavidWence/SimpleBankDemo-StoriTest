package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository

class GetName(private val repository: UserInfoRepository) {

    operator fun invoke() = repository.getName()

}