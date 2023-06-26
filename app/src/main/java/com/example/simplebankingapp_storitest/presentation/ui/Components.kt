package com.example.simplebankingapp_storitest.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.theme.BackgroundLoader


//etiquetas
@Composable
fun TitleText(text: String){
    Text(
        text = text,
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.primary)
}
@Composable
fun TitleText(@StringRes textId: Int){
    Text(
        text = stringResource(textId),
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.primary)
}

@Composable
fun SubtitleText(text: String, paddingTop: Int = 12){
    Text(
        modifier = Modifier.padding(0.dp,paddingTop.dp,0.dp,0.dp),
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 22.sp)
}
@Composable
fun SubtitleText(@StringRes textId: Int, paddingTop: Int = 12){
    Text(
        modifier = Modifier.padding(0.dp,paddingTop.dp,0.dp,0.dp),
        text = stringResource(textId),
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 22.sp)
}

@Composable
fun LabelText(@StringRes textId: Int, marginTop: Int = 12){
    Text(
        modifier = Modifier.padding(0.dp,marginTop.dp,0.dp,0.dp),
        text = stringResource(textId),
        fontSize = 16.sp)
}

//campos de entrada
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInputField(name: String,
                   onNameChanged: (String) -> Unit,
                   errorMessage: String?) {
    OutlinedTextField(
        value = name,
        singleLine = true,
        modifier = Modifier.padding(0.dp,40.dp,0.dp,0.dp),
        onValueChange = onNameChanged,
        label = { Text(stringResource(R.string.login_field_user)) },
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
fun EmailInputField(email: String,
                    onEmailChanged: (String) -> Unit,
                    errorMessage: String?) {
    OutlinedTextField(
        value = email,
        singleLine = true,
        modifier = Modifier.padding(0.dp,40.dp,0.dp,0.dp),
        onValueChange = onEmailChanged,
        label = { Text(stringResource(R.string.login_field_user)) },
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
        visualTransformation = if(passwordVisible)
            VisualTransformation.None else PasswordVisualTransformation()
    )
}

//elementos complejos
@Composable
fun LoaderFullscreen(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLoader)) {
        CircularProgressIndicator()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateUpAppBar(onNavigateUp: () -> Unit){
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.generic_cd_backscreen)
                )
            }
        }
    )
}