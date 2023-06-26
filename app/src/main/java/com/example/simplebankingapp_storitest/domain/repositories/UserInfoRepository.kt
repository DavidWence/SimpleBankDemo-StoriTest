package com.example.simplebankingapp_storitest.domain.repositories

import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.user.UserInfo

interface UserInfoRepository {

    fun setUserInfo(userInfo: UserInfo): Outcome<Nothing>

    suspend fun getBalance(): Outcome<Double>
    fun getName(): Outcome<String>
    fun getSurname(): Outcome<String>
    fun getEmail(): Outcome<String>

}