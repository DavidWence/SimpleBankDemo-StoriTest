package com.example.simplebankingapp_storitest.data

import com.example.simplebankingapp_storitest.data.repositories.FirebaseUsersRepository
import com.example.simplebankingapp_storitest.data.repositories.PrefsConfigurationRepository
import com.example.simplebankingapp_storitest.data.repositories.PrefsRegistrationRepository
import com.example.simplebankingapp_storitest.data.repositories.RealtimeUserInfoRepository
import com.example.simplebankingapp_storitest.data.utils.PreferenceComponent_LocalPreferences
import com.example.simplebankingapp_storitest.domain.repositories.ConfigurationRepository
import com.example.simplebankingapp_storitest.domain.repositories.RegistrationRepository
import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository
import com.example.simplebankingapp_storitest.domain.usecases.ValidateEmail
import com.example.simplebankingapp_storitest.domain.usecases.ValidateName
import com.example.simplebankingapp_storitest.domain.usecases.ValidatePassword
import com.example.simplebankingapp_storitest.domain.usecases.configuration.GetPreviousSession
import com.example.simplebankingapp_storitest.domain.usecases.registry.SaveUserInfo
import com.example.simplebankingapp_storitest.domain.usecases.registry.ValidateRepeatPassword
import com.example.simplebankingapp_storitest.domain.usecases.user.GetBalance
import com.example.simplebankingapp_storitest.domain.usecases.user.GetEmail
import com.example.simplebankingapp_storitest.domain.usecases.user.GetFullName
import com.example.simplebankingapp_storitest.domain.usecases.user.GetName
import com.example.simplebankingapp_storitest.domain.usecases.user.SignIn
import com.example.simplebankingapp_storitest.presentation.viewmodel.HomeViewModel
import com.example.simplebankingapp_storitest.presentation.viewmodel.SignInViewModel
import com.example.simplebankingapp_storitest.presentation.viewmodel.SignUpUserInfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        SignInViewModel(
            getPreviousSessionUseCase = get(),
            getNameUseCase = get(),
            getEmailUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            signInUseCase = get())
    }
    single { GetPreviousSession(repository = get()) }
    single {
        SignIn(
            usersRepository = get(),
            configurationRepository = get(),
            userInfoRepository = get())
    }
}

val registrationModule = module {
    viewModel {
        SignUpUserInfoViewModel(
            validateNameUseCase = get(),
            validateSurnameUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get(),
            validateRepeatPasswordUseCase = get(),
            saveUserInfoUseCase = get())
    }
    single { ValidateRepeatPassword() }
    single { SaveUserInfo(usersRepository = get(), registrationRepository = get()) }

    single<RegistrationRepository> { PrefsRegistrationRepository(prefs = get()) }
    single { PreferenceComponent_LocalPreferences.getInstance().LocalRegistrationInfo() }
}

val homeModule = module {
    viewModel {
        HomeViewModel(
            getBalanceUseCase = get(),
            getFullNameUseCase = get(),
            getEmailUseCase = get())
    }
}

val userInfoModule = module {
    single { ValidateEmail() }
    single { ValidatePassword() }
    single { ValidateName() }
    single { GetEmail(repository = get()) }
    single { GetName(repository = get()) }
    single { GetFullName(repository = get()) }
    single { GetBalance(repository = get()) }
}

val dataModule = module {
    single<ConfigurationRepository> { PrefsConfigurationRepository(prefs = get()) }
    single<UsersRepository> { FirebaseUsersRepository(auth = get(), database = get()) }
    single<UserInfoRepository> { RealtimeUserInfoRepository(userPrefs = get(), database = get()) }

    //proveedores de datos para repositorios
    single { PreferenceComponent_LocalPreferences.getInstance().LocalConfiguration() }
    single { PreferenceComponent_LocalPreferences.getInstance().LocalUserInfo() }
    single { FirebaseAuth.getInstance() }
    single { Firebase.database.reference }
}