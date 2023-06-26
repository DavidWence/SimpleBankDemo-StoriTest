package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.areAllOutcomesValids
import com.example.simplebankingapp_storitest.domain.usecases.configuration.GetPreviousSession
import com.example.simplebankingapp_storitest.domain.usecases.user.GetEmail
import com.example.simplebankingapp_storitest.domain.usecases.user.GetName
import com.example.simplebankingapp_storitest.domain.usecases.user.SignIn
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidateEmail
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidatePassword
import com.example.simplebankingapp_storitest.presentation.utils.UiState
import kotlinx.coroutines.launch

class SignInViewModel(private val getPreviousSessionUseCase: GetPreviousSession,
                      private val getNameUseCase: GetName,
                      private val getEmailUseCase: GetEmail,
                      private val validateEmailUseCase: ValidateEmail,
                      private val validatePasswordUseCase: ValidatePassword,
                      private val signInUseCase: SignIn):
    SimpleProcessViewModel() {

    //con sesión previa
    private var _withPreviousSession = false
    val withPreviousSession: Boolean get() = _withPreviousSession
    //datos de la sesión anterior
    private var _previousUserName: String? = null
    val previousUserName: String? get() = _previousUserName

    //credenciales del usuario
    var inputUser by mutableStateOf("")
        private set
    var inputPassword by mutableStateOf("")
        private set

    //errores de validación
    private val userError = MutableLiveData<String?>()
    val userErrorData: LiveData<String?> get() = userError
    private val passwordError = MutableLiveData<String?>()
    val passwordErrorData: LiveData<String?> get() = passwordError

    fun loadPreviousSession(){
        _withPreviousSession = getPreviousSessionUseCase().value ?: false
        _previousUserName = getNameUseCase().value
        viewModelScope.launch {
            inputUser = getEmailUseCase().value ?: ""
        }
    }

    fun updateUser(user: String){
        inputUser = user
    }

    fun updatePassword(password: String){
        inputPassword = password
    }

    fun login(){
        //se empiezan evaluando las precondiciones
        uiState.value = UiState.EvaluatingPreconditions
        val validEmail = validateEmailUseCase(inputUser)
        val validPassword = validatePasswordUseCase(inputPassword)
        //se muestran o quitan los errores según sea necesario
        userError.value =
            if(validEmail is Outcome.Error) validEmail.description.asText() else null
        passwordError.value =
            if(validPassword is Outcome.Error) validPassword.description.asText() else null

        //se continúa con el inicio de sesión solo si todas las precondiciones son válidas
        if(!areAllOutcomesValids(validEmail, validPassword))
            uiState.value = UiState.PreconditionsError()
        else {
            uiState.value = UiState.Loading
            viewModelScope.launch {
                when (val result = signInUseCase(inputUser, inputPassword)) {
                    is Outcome.Error -> uiState.value = UiState.Error(result.description.asText())
                    else -> uiState.value = UiState.Finished
                }
            }
        }
    }

}