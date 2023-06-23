package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.presentation.utils.ProcessStep
import kotlinx.coroutines.cancelChildren

abstract class SimpleProcessViewModel: ViewModel() {

    protected val step = MutableLiveData<ProcessStep>()
    val stepData: LiveData<ProcessStep> get() = step

    //para limpiar cualquier proceso dependiente
    override fun onCleared() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}