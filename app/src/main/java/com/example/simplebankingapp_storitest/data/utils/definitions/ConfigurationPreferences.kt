package com.example.simplebankingapp_storitest.data.utils.definitions

import com.skydoves.preferenceroom.EncryptEntity
import com.skydoves.preferenceroom.KeyName
import com.skydoves.preferenceroom.PreferenceEntity

@EncryptEntity("4BBC6FD973A9B4FD")
@PreferenceEntity("LocalConfiguration")
open class ConfigurationPreferences {

    @KeyName("withPreviousSession")
    @JvmField val previousSession: Boolean = false

}