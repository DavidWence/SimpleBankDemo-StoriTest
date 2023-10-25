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

    //se renderiza la información
    SignInContent(
        uiState,
        withPreviousSession = viewModel.withPreviousSession,
        previousUserName = viewModel.previousUserName,
        onSubmitUser = { viewModel.login() },
        user = viewModel.inputUser,
        onUserChanged = { viewModel.updateUser(it) },
        userError = userError,
        password = viewModel.inputPassword,
        onPasswordChanged = { viewModel.updatePassword(it) },
        passwordError = passwordError,
        onSignUpButtonClicked = onSignUpButtonClicked,
        onSignInCompleted = onSignInCompleted)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInContent(uiState: UiState = UiState.Idle,
                  withPreviousSession: Boolean = false,
                  previousUserName: String? = null,
                  onSubmitUser: () -> Unit = {},
                  user: String = "",
                  onUserChanged: (String) -> Unit = {},
                  userError: String? = null,
                  password: String = "",
                  onPasswordChanged: (String) -> Unit = {},
                  passwordError: String? = null,
                  onSignUpButtonClicked: () -> Unit = {},
                  onSignInCompleted: () -> Unit = {}){
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //cabecera
                HeaderText(R.string.login_title)
                if(withPreviousSession && previousUserName != null)
                    TitleText(stringResource(R.string.login_label_welcomeback, previousUserName))
                else
                    TitleText(R.string.login_label_subtitle)

                //campos de ingreso
                if(!withPreviousSession)
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
                    paddingTop = if(withPreviousSession) 40 else 8)
                ActionButton(
                    labelId = R.string.login_action_enter,
                    enabled = uiState.uiEnabled,
                    onButtonClicked = onSubmitUser)

                //registro
                LabelText(R.string.login_label_noaccount, 36)
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
                    message = uiState.message, duration = SnackbarDuration.Short)
            //avanzar al home si se logra el inicio de sesión
            else if(uiState is UiState.Finished)
                onSignInCompleted()
        }
    }
}

@Preview(showBackground = true, name = "Light", group = "Holo")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignInPreview() {
    SimpleBankingAppTheme {
        SignInContent()
    }
}

@Preview(showBackground = true, name = "Light", group = "Holo")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignInWithPreviousUserPreview() {
    SimpleBankingAppTheme {
        SignInContent(withPreviousSession = true, previousUserName = "David")
    }
}

@Preview(showBackground = true, name = "Light", group = "Holo")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignInLoadingPreview() {
    SimpleBankingAppTheme {
        SignInContent(uiState = UiState.Loading)
    }
}