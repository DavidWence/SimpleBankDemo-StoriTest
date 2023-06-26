package com.example.simplebankingapp_storitest.domain.usecases.configuration

import com.example.simplebankingapp_storitest.domain.repositories.ConfigurationRepository

class GetPreviousSession(private val repository: ConfigurationRepository) {

    operator fun invoke() = repository.getPreviousSession()

}