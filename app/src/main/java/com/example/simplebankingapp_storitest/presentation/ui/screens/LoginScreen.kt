package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.ProcessStep
import com.example.simplebankingapp_storitest.presentation.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = koinViewModel()){
    //se observa el estado de carga
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val currentStep = loginViewModel.stepData.observeAsState()
    when(val step = currentStep.value){
        is ProcessStep.Loading -> isLoading = true
        is ProcessStep.Error -> {
            isLoading = false
            Toast.makeText(LocalContext.current, step.message, Toast.LENGTH_SHORT).show()
        }
        is ProcessStep.Finished -> {
            isLoading = false
            (LocalContext.current as Activity).finish()
        }
        else -> Log.d("STEP", "step ${step?.toString()}: NO ACTION REQUIRED")
    }

    //se observan los errores de validación
    val userError = loginViewModel.userErrorData.observeAsState()
    val passwordError = loginViewModel.passwordErrorData.observeAsState()

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TitleText(R.string.login_title)
            SubtitleText(R.string.login_subtitle)
            UserInputField(
                onUserChanged = { loginViewModel.updateUser(it) },
                errorMessage = userError.value
            )
            PasswordInputField(
                onPasswordChanged = { loginViewModel.updatePassword(it) },
                onKeyboardDone = { loginViewModel.login() },
                errorMessage = passwordError.value
            )
            EnterButton(currentStep.value?.uiEnabled ?: true) { loginViewModel.login() }
            LabelText(R.string.login_label_noaccount, 36)
            RegisterButton()
        }
        if(isLoading)
            LoaderFullscreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputField(onUserChanged: (String) -> Unit,
                   errorMessage: String?) {
    var user by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = user,
        singleLine = true,
        modifier = Modifier.padding(0.dp,40.dp,0.dp,0.dp),
        onValueChange = {
            user = it
            onUserChanged(it) },
        label = { Text(stringResource(R.string.login_user)) },
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
        //fijar el tipo de teclado a email, acción para siguiente campo
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputField(onPasswordChanged: (String) -> Unit,
                       onKeyboardDone: () -> Unit,
                       errorMessage: String?) {
    val focusManager = LocalFocusManager.current
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        singleLine = true,
        modifier = Modifier.padding(0.dp,12.dp,0.dp,0.dp),
        onValueChange = {
            password = it
            onPasswordChanged(it) },
        label = { Text(stringResource(R.string.login_password)) },
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
fun EnterButton(enabled: Boolean, onSubmitUser: () -> Unit){
    var innerEnabled by rememberSaveable { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current

    ElevatedButton(
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

@Composable
fun RegisterButton(){
    TextButton(
        onClick = { }) {
        Text(stringResource(R.string.login_action_register))
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun DefaultPreview() {
    SimpleBankingAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen()
        }
    }
}