package com.example.simplebankingapp_storitest.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.areAllOutcomesValids
import com.example.simplebankingapp_storitest.domain.usecases.ValidateEmail
import com.example.simplebankingapp_storitest.domain.usecases.ValidateName
import com.example.simplebankingapp_storitest.domain.usecases.ValidatePassword
import com.example.simplebankingapp_storitest.domain.usecases.registry.SaveUserInfo
import com.example.simplebankingapp_storitest.domain.usecases.registry.ValidateRepeatPassword
import com.example.simplebankingapp_storitest.presentation.utils.UiState
import kotlinx.coroutines.launch

class SignUpUserInfoViewModel(private val validateNameUseCase: ValidateName,
                              private val validateSurnameUseCase: ValidateName,
                              private val validateEmailUseCase: ValidateEmail,
                              private val validatePasswordUseCase: ValidatePassword,
                              private val validateRepeatPasswordUseCase: ValidateRepeatPassword,
                              private val saveUserInfoUseCase: SaveUserInfo):
    SimpleProcessViewModel(){

    //datos del usuario
    var inputName by mutableStateOf("")
        private set
    var inputSurname by mutableStateOf("")
        private set
    var inputEmail by mutableStateOf("")
        private set
    var inputPassword by mutableStateOf("")
        private set
    var inputRepeatPassword by mutableStateOf("")
        private set

    //errores de validación
    private val nameError = MutableLiveData<String?>()
    val nameErrorData: LiveData<String?> get() = nameError
    private val surnameError = MutableLiveData<String?>()
    val surnameErrorData: LiveData<String?> get() = surnameError
    private val emailError = MutableLiveData<String?>()
    val emailErrorData: LiveData<String?> get() = emailError
    private val passwordError = MutableLiveData<String?>()
    val passwordErrorData: LiveData<String?> get() = passwordError
    private val repeatPasswordError = MutableLiveData<String?>()
    val repeatPasswordErrorData: LiveData<String?> get() = repeatPasswordError

    //actualizaciones de campos de entrada
    fun updateName(email: String){
        inputName = email
    }

    fun updateSurname(email: String){
        inputSurname = email
    }

    fun updateEmail(email: String){
        inputEmail = email
    }

    fun updatePassword(password: String){
        inputPassword = password
    }

    fun updateRepeatPassword(repeatPassword: String){
        inputRepeatPassword = repeatPassword
    }

    fun saveInfo(){
        //se evalúan las precondiciones
        uiState.value = UiState.EvaluatingPreconditions
        val validName = validateNameUseCase(
            inputName, ErrorDescription.NameEmpty, ErrorDescription.NameRegexMismatch)
        val validSurname = validateSurnameUseCase(
            inputSurname, ErrorDescription.SurnameEmpty, ErrorDescription.SurnameRegexMismatch)
        val validEmail = validateEmailUseCase(inputEmail)
        val validPassword = validatePasswordUseCase(inputPassword)
        val validRepeatPassword = validateRepeatPasswordUseCase(inputPassword, inputRepeatPassword)
        //se muestran o quitan los errores según sea necesario
        nameError.value =
            if(validName is Outcome.Error) validName.description.asText() else null
        surnameError.value =
            if(validSurname is Outcome.Error) validSurname.description.asText() else null
        emailError.value =
            if(validEmail is Outcome.Error) validEmail.description.asText() else null
        passwordError.value =
            if(validPassword is Outcome.Error) validPassword.description.asText() else null
        repeatPasswordError.value =
            if(validRepeatPassword is Outcome.Error) validRepeatPassword.description.asText() else null

        //se continúa con el registro solo si todas las precondiciones son válidas
        if(!areAllOutcomesValids(validName, validSurname, validEmail, validPassword, validRepeatPassword))
            uiState.value = UiState.PreconditionsError()
        else {
            uiState.value = UiState.Loading
            viewModelScope.launch {
                when (val result = saveUserInfoUseCase(inputName, inputSurname, inputEmail, inputPassword)) {
                    is Outcome.Error -> uiState.value = UiState.Error(result.description.asText())
                    else -> uiState.value = UiState.Finished
                }
            }
        }
    }

}