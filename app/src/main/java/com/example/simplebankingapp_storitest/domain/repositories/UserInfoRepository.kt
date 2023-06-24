package com.example.simplebankingapp_storitest.domain.repositories

import com.example.simplebankingapp_storitest.domain.entities.Outcome

interface UserInfoRepository {

    suspend fun getBalance(): Outcome<Double>

}