package com.example.simplebankingapp_storitest.data.utils.database

import com.example.simplebankingapp_storitest.domain.entities.user.UserInfo
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfoSnapshot(val email: String? = null,
                            val name: String? = null,
                            val surname: String? = null){

    //todos los datos deben de existir y ser válidos para tomarlos en cuenta
    fun toUserInfo() =
        if(!email.isNullOrEmpty() && !name.isNullOrEmpty() && !surname.isNullOrEmpty())
            UserInfo(email, name, surname)
        else
            null

}