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
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
fun HeaderText(text: String){
    Text(
        text = text,
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.primary)
}
@Composable
fun HeaderText(@StringRes textId: Int){
    Text(
        text = stringResource(textId),
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.primary)
}

@Composable
fun TitleText(text: String, paddingTop: Int = 12){
    Text(
        modifier = Modifier.padding(0.dp,paddingTop.dp,0.dp,0.dp),
        text = text,
        color = MaterialTheme.colorScheme.secondary,
        fontSize = 22.sp)
}
@Composable
fun TitleText(@StringRes textId: Int, paddingTop: Int = 12){
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
fun NameInputField(modifier: Modifier = Modifier,
                   @StringRes labelId: Int,
                   name: String,
                   onNameChanged: (String) -> Unit,
                   errorMessage: String? = null,
                   paddingTop: Int = 8) {
    OutlinedTextField(
        value = name,
        singleLine = true,
        modifier = Modifier.padding(0.dp,paddingTop.dp,0.dp,0.dp).then(modifier),
        onValueChange = onNameChanged,
        label = { Text(stringResource(labelId)) },
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
fun EmailInputField(modifier: Modifier = Modifier,
                    @StringRes labelId: Int = R.string.generic_field_email,
                    email: String,
                    onEmailChanged: (String) -> Unit,
                    errorMessage: String? = null,
                    paddingTop: Int = 8) {
    OutlinedTextField(
        value = email,
        singleLine = true,
        modifier = Modifier.padding(0.dp,paddingTop.dp,0.dp,0.dp).then(modifier),
        onValueChange = onEmailChanged,
        label = { Text(stringResource(labelId)) },
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
fun PasswordInputField(modifier: Modifier = Modifier,
                       @StringRes labelId: Int = R.string.generic_field_password,
                       canShowPassword: Boolean = true,
                       password: String,
                       onPasswordChanged: (String) -> Unit,
                       isFinalField: Boolean = true,
                       onKeyboardDone: () -> Unit = {},
                       errorMessage: String? = null,
                       paddingTop: Int = 8) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        singleLine = true,
        modifier = Modifier.padding(0.dp,paddingTop.dp,0.dp,0.dp).then(modifier),
        onValueChange = onPasswordChanged,
        label = { Text(stringResource(labelId)) },
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
            keyboardType = KeyboardType.Password,
            imeAction = if(isFinalField) ImeAction.Done else ImeAction.Next),
        keyboardActions = KeyboardActions(
            onDone = {
                onKeyboardDone()
                focusManager.clearFocus()
            }),
        //icono para mostrar/ocultar contraseña
        trailingIcon = {
            if(canShowPassword) {
                val icon =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = stringResource(
                    if (passwordVisible) R.string.generic_cd_hidepassword else R.string.generic_cd_showpassword
                )
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, description)
                }
            }
        },
        visualTransformation = if(passwordVisible && canShowPassword)
            VisualTransformation.None else PasswordVisualTransformation()
    )
}

//botones de acción
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ActionButton(modifier: Modifier = Modifier,
                 @StringRes labelId: Int,
                 enabled: Boolean = true,
                 onButtonClicked: () -> Unit,
                 padddingTop: Int = 40){
    var innerEnabled by rememberSaveable { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current

    ElevatedButton(
        //para evitar el doble click accidental
        enabled = innerEnabled || enabled,
        modifier = Modifier.padding(0.dp,padddingTop.dp,0.dp,0.dp).then(modifier),
        onClick = {
            keyboardController?.hide()
            innerEnabled = !innerEnabled
            onButtonClicked()
        }) {
        Text(stringResource(labelId))
    }
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