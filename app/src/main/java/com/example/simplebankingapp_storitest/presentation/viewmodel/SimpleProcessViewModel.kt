package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.presentation.utils.UiState
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class SimpleProcessViewModel: ViewModel() {

    protected val uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateData: StateFlow<UiState> get() = uiState

    //para limpiar cualquier proceso dependiente
    override fun onCleared() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}