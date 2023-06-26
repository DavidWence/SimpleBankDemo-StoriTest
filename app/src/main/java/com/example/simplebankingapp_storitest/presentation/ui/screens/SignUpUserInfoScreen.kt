package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.ActionButton
import com.example.simplebankingapp_storitest.presentation.ui.EmailInputField
import com.example.simplebankingapp_storitest.presentation.ui.LabelText
import com.example.simplebankingapp_storitest.presentation.ui.LoaderFullscreen
import com.example.simplebankingapp_storitest.presentation.ui.NameInputField
import com.example.simplebankingapp_storitest.presentation.ui.NavigateUpAppBar
import com.example.simplebankingapp_storitest.presentation.ui.PasswordInputField
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.UiState
import com.example.simplebankingapp_storitest.presentation.viewmodel.SignUpUserInfoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpUserInfoScreen(viewModel: SignUpUserInfoViewModel = koinViewModel(),
                         onNavigateUp: () -> Unit = {},
                         onInfoSaved: () -> Unit = {}) {

    //se observa el estado de carga
    val uiState by viewModel.uiStateData.collectAsState()

    //se observan los errores de validación
    val nameError by viewModel.nameErrorData.observeAsState(null)
    val surnameError by viewModel.surnameErrorData.observeAsState(null)
    val emailError by viewModel.emailErrorData.observeAsState(null)
    val passwordError by viewModel.passwordErrorData.observeAsState(null)
    val repeatPasswordError by viewModel.repeatPasswordErrorData.observeAsState(null)

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = { NavigateUpAppBar(onNavigateUp) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Column(
                Modifier
                    .width(IntrinsicSize.Max)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())) {
                TitleText(R.string.signup_userinfo_title)
                LabelText(R.string.signup_label_userinfo_subtitle)
                //campos de entrada
                NameInputField(
                    modifier = Modifier.fillMaxWidth(),
                    labelId = R.string.signup_field_name,
                    name = viewModel.inputName,
                    onNameChanged = { viewModel.updateName(it) },
                    errorMessage = nameError,
                    paddingTop = 24)
                NameInputField(
                    modifier = Modifier.fillMaxWidth(),
                    labelId = R.string.signup_field_surname,
                    name = viewModel.inputSurname,
                    onNameChanged = { viewModel.updateSurname(it) },
                    errorMessage = surnameError)
                EmailInputField(
                    modifier = Modifier.fillMaxWidth(),
                    email = viewModel.inputEmail,
                    onEmailChanged = { viewModel.updateEmail(it) },
                    errorMessage = emailError)
                PasswordInputField(
                    modifier = Modifier.fillMaxWidth(),
                    canShowPassword = false,
                    password = viewModel.inputPassword,
                    onPasswordChanged = { viewModel.updatePassword(it) },
                    isFinalField = false,
                    errorMessage = passwordError)
                PasswordInputField(
                    modifier = Modifier.fillMaxWidth(),
                    canShowPassword = false,
                    labelId = R.string.signup_field_repeatpassword,
                    password = viewModel.inputRepeatPassword,
                    onPasswordChanged = { viewModel.updateRepeatPassword(it) },
                    onKeyboardDone = { viewModel.saveInfo() },
                    errorMessage = repeatPasswordError)
                ActionButton(
                    labelId = R.string.generic_action_continue,
                    onButtonClicked = { viewModel.saveInfo() },
                    modifier = Modifier.align(CenterHorizontally))
            }
            //se muestra la pantalla de carga sobre los demás elementos
            if(!uiState.uiEnabled)
                LoaderFullscreen()
        }

        LaunchedEffect(uiState, snackbarHostState){
            //mostrar mensaje de error
            if(uiState is UiState.Error)
                snackbarHostState.showSnackbar(
                    message = (uiState as UiState.Error).message, duration = SnackbarDuration.Short)
            //avanzar al home si se logra el inicio de sesión
            else if(uiState is UiState.Finished)
                onInfoSaved()
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignUpUserInfoPreview() {
    SimpleBankingAppTheme {
        SignUpUserInfoScreen()
    }
}