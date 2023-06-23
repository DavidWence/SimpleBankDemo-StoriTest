package com.example.simplebankingapp_storitest.data

import com.example.simplebankingapp_storitest.data.repositories.FirebaseUsersRepository
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository
import com.example.simplebankingapp_storitest.domain.usecases.user.SignIn
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidateEmail
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidatePassword
import com.example.simplebankingapp_storitest.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel(
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            signInUseCase = get())
    }
    single { ValidateEmail() }
    single { ValidatePassword() }
    single { SignIn(usersRepository = get()) }
}

val dataModule = module {
    single<UsersRepository> { FirebaseUsersRepository() }
}