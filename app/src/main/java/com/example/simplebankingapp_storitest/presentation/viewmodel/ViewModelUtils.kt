package com.example.simplebankingapp_storitest.presentation.viewmodel

import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.presentation.App

fun ErrorDescription.asText(): String =
    when(this){
        //errores genéricos
        ErrorDescription.UnknownError -> App.getString(R.string.generic_error_unknown)
        ErrorDescription.MissingValueError -> App.getString(R.string.generic_error_missingvalue)
        //errores de formato
        ErrorDescription.EmailEmpty -> App.getString(R.string.error_noemail)
        ErrorDescription.EmailRegexMismatch -> App.getString(R.string.error_invalidemail)
        ErrorDescription.PasswordEmpty -> App.getString(R.string.error_nopassword)
        ErrorDescription.PasswordNotMinimumLenght -> App.getString(R.string.error_passwordminimumlenght)
        ErrorDescription.NameEmpty -> App.getString(R.string.signup_error_noname)
        ErrorDescription.NameRegexMismatch -> App.getString(R.string.signup_error_invalidname)
        ErrorDescription.SurnameEmpty -> App.getString(R.string.signup_error_nosurname)
        ErrorDescription.SurnameRegexMismatch -> App.getString(R.string.signup_error_invalidsurname)
        //errores de sesión
        ErrorDescription.UserNotFound, ErrorDescription.UserWrongCredentials ->
            App.getString(R.string.login_error_usernotfound)
        //errores de registro
        ErrorDescription.RepeatPasswordEmpty -> App.getString(R.string.signup_error_norepeatpassword)
        ErrorDescription.RepeatPasswordNotEqual ->
            App.getString(R.string.signup_error_repeatpasswordmismatch)
        ErrorDescription.UserAlreadyRegistered -> App.getString(R.string.signup_error_existinguser)
        //errores de conexión
        ErrorDescription.ServerUnavailable -> App.getString(R.string.error_serverunavailable)
    }