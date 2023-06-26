package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.LabelText
import com.example.simplebankingapp_storitest.presentation.ui.LoaderFullscreen
import com.example.simplebankingapp_storitest.presentation.ui.SubtitleText
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.EmailInputField
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.UiState
import com.example.simplebankingapp_storitest.presentation.viewmodel.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(signInViewModel: SignInViewModel = koinViewModel(),
                 onSignUpButtonClicked: () -> Unit = {},
                 onSignInCompleted: () -> Unit = {}) {
    //se precargan los datos de sesión previa
    signInViewModel.loadPreviousSession()

    //se observa el estado de carga
    val uiState by signInViewModel.uiStateData.collectAsState()

    //se observan los errores de validación
    val userError by signInViewModel.userErrorData.observeAsState(null)
    val passwordError by signInViewModel.passwordErrorData.observeAsState(null)

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //cabecera
                Header(
                    withPreviousSession = signInViewModel.withPreviousSession,
                    previousUserName = signInViewModel.previousUserName
                )
                //campos de ingreso
                InputFields(
                    withPreviousUser = signInViewModel.withPreviousSession,
                    user = signInViewModel.inputUser,
                    onUserChanged = { signInViewModel.updateUser(it) },
                    password = signInViewModel.inputPassword,
                    onPasswordChanged = { signInViewModel.updatePassword(it) },
                    userError = userError,
                    passwordError = passwordError,
                    uiEnabled = uiState.uiEnabled
                ) {
                    signInViewModel.login()
                }
                LabelText(R.string.login_label_noaccount, 36)
                //botón de registro
                TextButton(onClick = onSignUpButtonClicked) {
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
    TitleText(R.string.login_title)
    if(withPreviousSession && previousUserName != null) {
        SubtitleText(stringResource(R.string.login_label_welcomeback, previousUserName))
        Spacer(modifier = Modifier.height(28.dp))
    } else
        SubtitleText(R.string.login_label_subtitle)
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
    //al tener una sesión iniciada no se requiere mostrar el campo para correo
    if(!withPreviousUser)
        EmailInputField(
            email = user,
            onEmailChanged = onUserChanged,
            errorMessage = userError)
    PasswordInputField(
        password = password,
        onPasswordChanged = onPasswordChanged,
        onKeyboardDone = onSubmitUser,
        errorMessage = passwordError
    )
    EnterButton(uiEnabled, onSubmitUser)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(password: String,
                       onPasswordChanged: (String) -> Unit,
                       onKeyboardDone: () -> Unit,
                       errorMessage: String?) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        singleLine = true,
        modifier = Modifier.padding(0.dp,12.dp,0.dp,0.dp),
        onValueChange = onPasswordChanged,
        label = { Text(stringResource(R.string.login_field_password)) },
        //mensaje de error
        isError = !errorMessage.isNullOrEmpty(),
        supportingText = {
            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        //fijar tipo de teclado a contraseña, ejecutar acción al finalizar ingreso de valores
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onKeyboardDone()
                focusManager.clearFocus()
            }),
        //icono para mostrar/ocultar contraseña
        trailingIcon = {
            val icon = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = stringResource(
                if (passwordVisible) R.string.login_cd_hidepassword else R.string.login_cd_showpassword)
            IconButton(onClick = { passwordVisible = !passwordVisible }){
                Icon(icon, description)
            }
        },
        visualTransformation =
        if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EnterButton(enabled: Boolean = true, onSubmitUser: () -> Unit){
    var innerEnabled by rememberSaveable { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current

    ElevatedButton(
        //para evitar el doble click accidental
        enabled = innerEnabled || enabled,
        modifier = Modifier.padding(0.dp,40.dp,0.dp,0.dp),
        onClick = {
            keyboardController?.hide()
            innerEnabled = !innerEnabled
            onSubmitUser()
        }) {
        Text(stringResource(R.string.login_action_enter))
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignInPreview() {
    SimpleBankingAppTheme {
        SignInScreen()
    }
}