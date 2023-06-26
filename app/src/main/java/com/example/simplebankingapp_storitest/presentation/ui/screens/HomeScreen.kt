@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.HeaderText
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.asCurrency
import com.example.simplebankingapp_storitest.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(),
               drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
               onExitApp: () -> Unit = {}) {
    //se observan los datos de la cuenta
    val balance = viewModel.balanceData.observeAsState()
    val fullName by viewModel.fullNameData.observeAsState("")
    val email by viewModel.emailData.observeAsState("")

    viewModel.loadInitialInfo()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { HomeDrawer(fullName, email) }) {
        Scaffold(topBar = { HomeAppBar(drawerState = drawerState) }) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)) {
                    TitleText(R.string.home_label_balance, paddingTop = 8)
                    HeaderText(balance.value?.asCurrency() ?: "-")
                }
            }
        }
    }

    //se maneja el evento de "back" para cerrar el drawer o salir de la app
    val scope = rememberCoroutineScope()
    BackHandler(enabled = true) {
        if(drawerState.isOpen)
            scope.launch { drawerState.close() }
        else
            onExitApp()
    }
}

@Composable
fun HomeAppBar(drawerState: DrawerState){
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                Icon(Icons.Rounded.Menu, contentDescription = "MenuButton")
            }
        },
    )
}

@Composable
fun HomeDrawer(fullName: String, email: String){
    ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(fullName)
            Text(email)
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun HomePreview() {
    SimpleBankingAppTheme {
        HomeScreen()
    }
}