package com.example.simplebankingapp_storitest.data

import com.example.simplebankingapp_storitest.data.repositories.FirebaseUsersRepository
import com.example.simplebankingapp_storitest.data.repositories.RealtimeUserInfoRepository
import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository
import com.example.simplebankingapp_storitest.domain.usecases.user.GetBalance
import com.example.simplebankingapp_storitest.domain.usecases.user.SignIn
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidateEmail
import com.example.simplebankingapp_storitest.domain.usecases.userinfo.ValidatePassword
import com.example.simplebankingapp_storitest.presentation.viewmodel.HomeViewModel
import com.example.simplebankingapp_storitest.presentation.viewmodel.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        SignInViewModel(
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            signInUseCase = get())
    }
    single { ValidateEmail() }
    single { ValidatePassword() }
    single { SignIn(usersRepository = get()) }
}

val homeModule = module {
    viewModel {
        HomeViewModel(getBalanceUseCase = get())
    }
    single { GetBalance(userInfoRepository = get()) }
}

val dataModule = module {
    single<UsersRepository> { FirebaseUsersRepository() }
    single<UserInfoRepository> { RealtimeUserInfoRepository() }
}