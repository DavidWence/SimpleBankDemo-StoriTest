package com.example.simplebankingapp_storitest.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplebankingapp_storitest.presentation.ui.theme.BackgroundLoader

@Composable
fun TitleText(@StringRes textId: Int){
    Text(
        text = stringResource(textId),
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.onPrimary)
}

@Composable
fun SubtitleText(@StringRes textId: Int){
    Text(
        modifier = Modifier.padding(0.dp,12.dp,0.dp,0.dp),
        text = stringResource(textId),
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
        modifier = Modifier.fillMaxSize().background(BackgroundLoader)) {
        CircularProgressIndicator()
    }
}