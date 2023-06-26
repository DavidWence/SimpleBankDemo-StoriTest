package com.example.simplebankingapp_storitest.presentation.utils

@Suppress("unused")
sealed class UiState(val uiEnabled: Boolean) {
    //estado inicial de la UI
    object Idle: UiState(true)
    //paso inicial opcional, por si se tiene que validar precondiciones
    object EvaluatingPreconditions: UiState(false)
    //paso final alternativo, por si existen errores en las precondiciones
    data class PreconditionsError(val message: String? = null): UiState(true)
    //carga del proceso principal
    object Loading: UiState(false)
    //error en el proceso principal
    data class Error(val message: String): UiState(true)
    //finalización del proceso principal
    object Finished: UiState(true)
}