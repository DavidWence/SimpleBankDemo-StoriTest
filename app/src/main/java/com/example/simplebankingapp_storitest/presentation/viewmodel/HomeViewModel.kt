package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.domain.usecases.user.GetBalance
import com.example.simplebankingapp_storitest.domain.usecases.user.GetEmail
import com.example.simplebankingapp_storitest.domain.usecases.user.GetFullName
import kotlinx.coroutines.launch

class HomeViewModel(private val getBalanceUseCase: GetBalance,
                    private val getFullNameUseCase: GetFullName,
                    private val getEmailUseCase: GetEmail):
    SimpleProcessViewModel() {

    private val _balance = MutableLiveData<Double?>()
    val balanceData: LiveData<Double?> get() = _balance

    private val _fullname = MutableLiveData<String>()
    val fullNameData: LiveData<String> get() = _fullname

    private val _email = MutableLiveData<String>()
    val emailData: LiveData<String> get() = _email

    fun loadInitialInfo(){
        viewModelScope.launch {
            _balance.value = getBalanceUseCase().value
            _fullname.value = getFullNameUseCase().value
            _email.value = getEmailUseCase().value
        }
    }

}