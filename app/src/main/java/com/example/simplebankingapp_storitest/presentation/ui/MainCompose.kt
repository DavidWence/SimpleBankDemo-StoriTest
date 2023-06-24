package com.example.simplebankingapp_storitest.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplebankingapp_storitest.presentation.ui.screens.HomeScreen
import com.example.simplebankingapp_storitest.presentation.ui.screens.SignInScreen
import com.example.simplebankingapp_storitest.presentation.ui.screens.SignUpScreen
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose(){
    SimpleBankingAppTheme {
        //mapa de navegación
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = AppScreens.SignIn.name){
            composable(AppScreens.SignIn.name){
                SignInScreen(
                    onSignUpButtonClicked = { navController.navigate(AppScreens.SingUp.name) },
                    onSignInCompleted = { navigateToHome(navController) })
            }
            composable(AppScreens.SingUp.name){ SignUpScreen(navController) }
            composable(AppScreens.Home.name){ HomeScreen() }
        }

        //si ya hay una sesión activa se manda directo al home
        if(FirebaseAuth.getInstance().currentUser != null)
            navigateToHome(navController)
    }
}

private fun navigateToHome(navController: NavHostController){
    navController.navigate(AppScreens.Home.name){
        popUpTo(AppScreens.SignIn.name) { inclusive = true }
    }
}

enum class AppScreens {
    SignIn,
    SingUp,
    Home
}