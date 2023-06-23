package com.example.simplebankingapp_storitest.domain.entities

sealed class ErrorDescription {
    //error genérico
    object UnknownError: ErrorDescription()

    //Errores de formato
    //el email está vacío
    object EmailEmpty: ErrorDescription()
    //el email no cumple con el regex
    object EmailRegexMismatch: ErrorDescription()
    //la contraseña está vacía
    object PasswordEmpty: ErrorDescription()

    //Errores de conexión
    object ServerUnavailable: ErrorDescription()

    //Errores de sesión
    //el usuario no existe
    object UserNotFound: ErrorDescription()
    //credenciales incorrectas
    object UserWrongCredentials: ErrorDescription()
}