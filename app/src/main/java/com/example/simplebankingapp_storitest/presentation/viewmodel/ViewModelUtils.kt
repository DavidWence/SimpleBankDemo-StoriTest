package com.example.simplebankingapp_storitest.presentation.viewmodel

import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.presentation.App

fun ErrorDescription.asText(): String =
    when(this){
        ErrorDescription.UnknownError -> App.getString(R.string.error_generic)
        ErrorDescription.EmailEmpty -> App.getString(R.string.error_noemail)
        ErrorDescription.EmailRegexMismatch -> App.getString(R.string.error_invalidemail)
        ErrorDescription.PasswordEmpty -> App.getString(R.string.error_nopassword)
        ErrorDescription.UserNotFound, ErrorDescription.UserWrongCredentials ->
            App.getString(R.string.error_usernotfound)
        ErrorDescription.ServerUnavailable -> App.getString(R.string.error_serverunavailable)
    }