package com.example.simplebankingapp_storitest.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.theme.BackgroundLoader

@Composable
fun TitleText(@StringRes textId: Int){
    Text(
        text = stringResource(textId),
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.primary)
}
@Composable
fun TitleText(text: String){
    Text(
        text = text,
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.primary)
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