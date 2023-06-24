@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.content.res.Configuration
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.SubtitleText
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.asCurrency
import com.example.simplebankingapp_storitest.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = koinViewModel(),
               drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)) {
    //se observan los datos de la cuenta
    val balance = homeViewModel.balanceData.observeAsState()

    //se cargan los datos iniciales
    LaunchedEffect(Unit){
        homeViewModel.loadInitialInfo()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { HomeDrawer() }) {
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
                    SubtitleText(R.string.home_label_balance, paddingTop = 8)
                    TitleText(balance.value?.asCurrency() ?: "-")
                }
            }
        }
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
fun HomeDrawer(){
    ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.background) {
        Text("Hello World!")
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