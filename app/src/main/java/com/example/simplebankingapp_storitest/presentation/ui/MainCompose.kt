package com.example.simplebankingapp_storitest.presentation.ui

import android.app.Activity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplebankingapp_storitest.presentation.ui.screens.HomeScreen
import com.example.simplebankingapp_storitest.presentation.ui.screens.SignInScreen
import com.example.simplebankingapp_storitest.presentation.ui.screens.SignUpScreen
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose(){
    val activity = LocalContext.current as? Activity
    SimpleBankingAppTheme {
        //mapa de navegación
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = AppScreens.SignIn.name){
            composable(AppScreens.SignIn.name){
                SignInScreen(
                    onSignUpButtonClicked = { navController.navigate(AppScreens.SingUp.name) },
                    onSignInCompleted = { navController.navigate(AppScreens.Home.name) })
            }
            composable(AppScreens.Home.name){ HomeScreen(onExitApp = { activity?.finish() }) }
            composable(AppScreens.SingUp.name){
                SignUpScreen(onNavigateUp = { navController.navigateUp() })
            }
        }
    }
}

enum class AppScreens {
    SignIn,
    SingUp,
    Home
}