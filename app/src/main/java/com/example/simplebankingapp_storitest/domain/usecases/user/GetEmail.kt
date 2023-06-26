package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository

class GetEmail(private val repository: UserInfoRepository) {

    operator fun invoke() = repository.getEmail()

}