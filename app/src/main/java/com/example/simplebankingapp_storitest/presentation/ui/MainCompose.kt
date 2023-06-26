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
import com.example.simplebankingapp_storitest.presentation.ui.screens.SignUpPhotoIdScreen
import com.example.simplebankingapp_storitest.presentation.ui.screens.SignUpUserInfoScreen
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose(){
    val activity = LocalContext.current as? Activity
    SimpleBankingAppTheme {
        //mapa de navegación
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = AppScreens.SignIn.name){
            //inicio de sesión
            composable(AppScreens.SignIn.name){
                SignInScreen(
                    onSignUpButtonClicked = { navController.navigate(AppScreens.SignUpUserInfo.name) },
                    onSignInCompleted = { navController.navigate(AppScreens.Home.name) })
            }
            //registro
            composable(AppScreens.SignUpUserInfo.name){
                SignUpUserInfoScreen(
                    onNavigateUp = { navController.navigateUp() },
                    onInfoSaved = { navController.navigate(AppScreens.SignUpPhotoId.name) })
            }
            composable(AppScreens.SignUpPhotoId.name){
                SignUpPhotoIdScreen(
                    onNavigateUp = {
                        navController.navigate(AppScreens.SignIn.name) {
                            popUpTo(AppScreens.SignIn.name){ inclusive = true }
                        }
                    },
                    onPhotoTaken = { navController.navigate(AppScreens.SignUpUploadInfo.name) })
            }
            //pantalla principal
            composable(AppScreens.Home.name){ HomeScreen(onExitApp = { activity?.finish() }) }
        }
    }
}

enum class AppScreens {
    SignIn,
    SignUpUserInfo,
    SignUpPhotoId,
    SignUpUploadInfo,
    Home
}