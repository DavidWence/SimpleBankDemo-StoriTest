package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository

class GetBalance(private val repository: UserInfoRepository) {

    suspend operator fun invoke() = repository.getBalance()

}