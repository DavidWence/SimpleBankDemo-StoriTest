package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.ActionButton
import com.example.simplebankingapp_storitest.presentation.ui.EmailInputField
import com.example.simplebankingapp_storitest.presentation.ui.HeaderText
import com.example.simplebankingapp_storitest.presentation.ui.LabelText
import com.example.simplebankingapp_storitest.presentation.ui.LoaderFullscreen
import com.example.simplebankingapp_storitest.presentation.ui.PasswordInputField
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.UiState
import com.example.simplebankingapp_storitest.presentation.viewmodel.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(viewModel: SignInViewModel = koinViewModel(),
                 onSignUpButtonClicked: () -> Unit = {},
                 onSignInCompleted: () -> Unit = {}) {
    //se precargan los datos de sesión previa
    viewModel.loadPreviousSession()

    //se observa el estado de carga
    val uiState by viewModel.uiStateData.collectAsState()

    //se observan los errores de validación
    val userError by viewModel.userErrorData.observeAsState(null)
    val passwordError by viewModel.passwordErrorData.observeAsState(null)

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //cabecera
                Header(
                    withPreviousSession = viewModel.withPreviousSession,
                    previousUserName = viewModel.previousUserName
                )
                //campos de ingreso
                InputFields(
                    withPreviousUser = viewModel.withPreviousSession,
                    user = viewModel.inputUser,
                    onUserChanged = { viewModel.updateUser(it) },
                    password = viewModel.inputPassword,
                    onPasswordChanged = { viewModel.updatePassword(it) },
                    userError = userError,
                    passwordError = passwordError,
                    uiEnabled = uiState.uiEnabled
                ) {
                    viewModel.login()
                }
                LabelText(R.string.login_label_noaccount, 36)
                //botón de registro
                TextButton(
                    onClick = onSignUpButtonClicked,
                    modifier = Modifier.padding(0.dp,8.dp,0.dp,0.dp)) {
                    Text(stringResource(R.string.login_action_register))
                }
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
                onSignInCompleted()
        }
    }
}

@Composable
fun Header(withPreviousSession: Boolean, previousUserName: String?){
    HeaderText(R.string.login_title)
    if(withPreviousSession && previousUserName != null)
        TitleText(stringResource(R.string.login_label_welcomeback, previousUserName))
    else
        TitleText(R.string.login_label_subtitle)
}

@Composable
fun InputFields(withPreviousUser: Boolean,
                user: String,
                onUserChanged: (String) -> Unit,
                password: String,
                onPasswordChanged: (String) -> Unit,
                userError: String?,
                passwordError: String?,
                uiEnabled: Boolean,
                onSubmitUser: () -> Unit){
    //solo se muestra el campo para correo si no hay una sesión previa
    if(!withPreviousUser)
        EmailInputField(
            labelId = R.string.login_field_user,
            email = user,
            onEmailChanged = onUserChanged,
            errorMessage = userError,
            paddingTop = 40)
    PasswordInputField(
        password = password,
        onPasswordChanged = onPasswordChanged,
        onKeyboardDone = onSubmitUser,
        errorMessage = passwordError,
        paddingTop = if(withPreviousUser) 40 else 8)
    ActionButton(
        labelId = R.string.login_action_enter,
        enabled = uiEnabled,
        onButtonClicked = onSubmitUser)
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignInPreview() {
    SimpleBankingAppTheme {
        SignInScreen()
    }
}