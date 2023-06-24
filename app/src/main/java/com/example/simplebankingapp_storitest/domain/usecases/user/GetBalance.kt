package com.example.simplebankingapp_storitest.domain.usecases.user

import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository

class GetBalance(private val userInfoRepository: UserInfoRepository) {

    suspend operator fun invoke() = userInfoRepository.getBalance()

}