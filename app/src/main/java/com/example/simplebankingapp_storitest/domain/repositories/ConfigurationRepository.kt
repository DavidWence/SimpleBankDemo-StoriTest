package com.example.simplebankingapp_storitest.domain.repositories

import com.example.simplebankingapp_storitest.domain.entities.Outcome

interface ConfigurationRepository {

    fun getPreviousSession(): Outcome<Boolean>
    fun setActiveSession(active: Boolean): Outcome<Nothing>

}