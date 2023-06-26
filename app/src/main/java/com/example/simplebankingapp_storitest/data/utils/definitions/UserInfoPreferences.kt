package com.example.simplebankingapp_storitest.data.utils.definitions

import com.skydoves.preferenceroom.EncryptEntity
import com.skydoves.preferenceroom.KeyName
import com.skydoves.preferenceroom.PreferenceEntity

@EncryptEntity("F3D3E811323839AB")
@PreferenceEntity("LocalUserInfo")
open class UserInfoPreferences {

    @KeyName("email")
    @JvmField val userEmail: String? = null

    @KeyName("name")
    @JvmField val userName: String? = null

    @KeyName("surname")
    @JvmField val userSurname: String? = null

}