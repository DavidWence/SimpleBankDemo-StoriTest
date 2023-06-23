package com.example.simplebankingapp_storitest.domain.entities

sealed class Outcome<out T> {
    data class Success<out T>(val data: T): Outcome<T>()
    data class Error(val description: ErrorDescription): Outcome<Nothing>()
    object Completed: Outcome<Nothing>()

    //se obtiene el resultado directamente
    val value: T? get() = if(this is Success) data else null
}

fun areAllOutcomesValids(vararg outcomes: Outcome<Any>): Boolean {
    for (outcome in outcomes)
        if (outcome is Outcome.Error)
            return false
    return true
}