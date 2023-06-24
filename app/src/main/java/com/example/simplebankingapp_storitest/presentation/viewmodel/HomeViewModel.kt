package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.domain.usecases.user.GetBalance
import kotlinx.coroutines.launch

class HomeViewModel(private val getBalanceUseCase: GetBalance): SimpleProcessViewModel() {

    private val _balance = MutableLiveData<Double?>()
    val balanceData: LiveData<Double?> get() = _balance

    fun loadInitialInfo(){
        viewModelScope.launch {
            _balance.value = getBalanceUseCase().value
        }
    }

}