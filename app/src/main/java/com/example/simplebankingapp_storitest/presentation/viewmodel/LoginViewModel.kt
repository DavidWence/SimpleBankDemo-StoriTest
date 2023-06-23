package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.areAllOutcomesValids
import com.example.simplebankingapp_storitest.domain.usecases.user.SignIn
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidateEmail
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidatePassword
import com.example.simplebankingapp_storitest.presentation.utils.ProcessStep
import kotlinx.coroutines.launch

class LoginViewModel(private val validateEmailUseCase: ValidateEmail,
                     private val validatePasswordUseCase: ValidatePassword,
                     private val signInUseCase: SignIn):
    SimpleProcessViewModel() {

    private var user = ""
    private var password = ""

    private val userError = MutableLiveData<String?>()
    val userErrorData: LiveData<String?> get() = userError

    private val passwordError = MutableLiveData<String?>()
    val passwordErrorData: LiveData<String?> get() = passwordError

    fun updateUser(inputUser: String){
        user = inputUser
    }

    fun updatePassword(inputPassword: String){
        password = inputPassword
    }

    fun login(){
        //se empiezan evaluando las precondiciones
        step.value = ProcessStep.Preconditions
        val validEmail = validateEmailUseCase(user)
        val validPassword = validatePasswordUseCase(password)
        //se muestran o quitan los errores según sea necesario
        userError.value =
            if(validEmail is Outcome.Error) validEmail.description.asText() else null
        passwordError.value =
            if(validPassword is Outcome.Error) validPassword.description.asText() else null

        //se continúa con el inicio de sesión solo si todas las precondiciones son válidas
        if(!areAllOutcomesValids(validEmail, validPassword))
            step.value = ProcessStep.PreconditionsError()
        else {
            viewModelScope.launch {
                step.value = ProcessStep.Loading()
                when (val result = signInUseCase(user, password)) {
                    is Outcome.Error -> step.value = ProcessStep.Error(result.description.asText())
                    else -> {
                        step.value = ProcessStep.Finished
                        //se limpia para volver a la navegación
                        step.value = null
                    }
                }
            }
        }

    }

}