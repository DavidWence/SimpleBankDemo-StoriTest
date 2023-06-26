package com.example.simplebankingapp_storitest.data.repositories

import com.example.simplebankingapp_storitest.data.utils.definitions.Preference_LocalConfiguration
import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.asOutcome
import com.example.simplebankingapp_storitest.domain.entities.outcomeCompleted
import com.example.simplebankingapp_storitest.domain.repositories.ConfigurationRepository

class PrefsConfigurationRepository(private val prefs: Preference_LocalConfiguration):
    ConfigurationRepository {

    override fun getPreviousSession() =
        prefs.withPreviousSession.asOutcome(ErrorDescription.MissingValueError)

    override fun setActiveSession(active: Boolean) =
        outcomeCompleted { prefs.putWithPreviousSession(active) }

}