package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.NavigateUpAppBar
import com.example.simplebankingapp_storitest.presentation.ui.SubtitleText
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(onNavigateUp: () -> Unit = {}) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = { NavigateUpAppBar(onNavigateUp) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                TitleText(R.string.registry_title)
                SubtitleText(R.string.registry_label_subtitle)
            }
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignUpPreview() {
    SimpleBankingAppTheme {
        SignUpScreen()
    }
}