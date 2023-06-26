package com.example.simplebankingapp_storitest.data.repositories

import com.example.simplebankingapp_storitest.data.utils.definitions.Preference_LocalRegistrationInfo
import com.example.simplebankingapp_storitest.domain.entities.outcomeCompleted
import com.example.simplebankingapp_storitest.domain.repositories.RegistrationRepository

class PrefsRegistrationRepository(private val prefs: Preference_LocalRegistrationInfo):
    RegistrationRepository {

    override fun setUserInfo(name: String, surname: String, email: String, password: String) =
        outcomeCompleted {
            prefs.putName(name)
            prefs.putSurname(name)
            prefs.putEmail(name)
            prefs.putPassword(name)
        }

}