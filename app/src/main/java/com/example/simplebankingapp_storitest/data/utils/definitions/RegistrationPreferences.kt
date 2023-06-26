package com.example.simplebankingapp_storitest.data.utils.definitions

import com.skydoves.preferenceroom.EncryptEntity
import com.skydoves.preferenceroom.KeyName
import com.skydoves.preferenceroom.PreferenceEntity

@EncryptEntity("C8EFAF82B8DDDB97")
@PreferenceEntity("LocalRegistrationInfo")
open class RegistrationPreferences {

    @KeyName("email")
    @JvmField val userEmail: String? = null

    @KeyName("password")
    @JvmField val userPassword: String? = null

    @KeyName("name")
    @JvmField val userName: String? = null

    @KeyName("surname")
    @JvmField val userSurname: String? = null

}