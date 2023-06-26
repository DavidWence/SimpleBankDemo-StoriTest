package com.example.simplebankingapp_storitest.domain.repositories

import com.example.simplebankingapp_storitest.domain.entities.Outcome

interface RegistrationRepository {

    fun setUserInfo(name: String, surname: String, email: String, password: String): Outcome<Nothing>

}