package com.example.simplebankingapp_storitest.domain.entities

sealed class ErrorDescription {
    //errores genéricos
    object UnknownError: ErrorDescription()
    object MissingValueError: ErrorDescription()

    //errores de formato
    //el email está vacío
    object EmailEmpty: ErrorDescription()
    //el email no cumple con el regex
    object EmailRegexMismatch: ErrorDescription()
    //la contraseña está vacía
    object PasswordEmpty: ErrorDescription()
    //la contraseña no tiene la longitud mínima
    object PasswordNotMinimumLenght: ErrorDescription()
    //el nombre está vacío
    object NameEmpty: ErrorDescription()
    //el nombre no cumple con el regex
    object NameRegexMismatch: ErrorDescription()
    //el apellido está vacío
    object SurnameEmpty: ErrorDescription()
    //el apellido no cumple con el regex
    object SurnameRegexMismatch: ErrorDescription()

    //errores de inicio sesión
    //el usuario no existe
    object UserNotFound: ErrorDescription()
    //credenciales incorrectas
    object UserWrongCredentials: ErrorDescription()

    //errores de registro
    //el usuario ya existe
    object UserAlreadyRegistered: ErrorDescription()
    object RepeatPasswordEmpty: ErrorDescription()
    object RepeatPasswordNotEqual: ErrorDescription()

    //Errores de conexión
    object ServerUnavailable: ErrorDescription()
}