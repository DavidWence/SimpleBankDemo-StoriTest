package com.example.simplebankingapp_storitest.presentation.utils

sealed class ProcessStep(val uiEnabled: Boolean) {
    //paso inicial opcional, por si se tiene que validar precondiciones
    object Preconditions: ProcessStep(false)
    //paso final alternativo, por si existen errores en las precondiciones, puede ir acompañado de extras
    data class PreconditionsError(val message: String? = null, val extras: Any? = null): ProcessStep(true)
    //carga del proceso base
    data class Loading(val progress: Any? = null): ProcessStep(false)
    //error en el proceso base
    data class Error(val message: String): ProcessStep(true)
    //finalización del proceso base
    object Finished: ProcessStep(true)
}