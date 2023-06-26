package com.example.simplebankingapp_storitest.data.utils

import com.example.simplebankingapp_storitest.data.utils.definitions.ConfigurationPreferences
import com.example.simplebankingapp_storitest.data.utils.definitions.UserInfoPreferences
import com.skydoves.preferenceroom.PreferenceComponent

@PreferenceComponent(entities = [ConfigurationPreferences::class, UserInfoPreferences::class])
interface LocalPreferences