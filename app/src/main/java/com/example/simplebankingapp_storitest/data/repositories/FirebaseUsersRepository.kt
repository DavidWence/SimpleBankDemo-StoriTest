package com.example.simplebankingapp_storitest.data.repositories

import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseUsersRepository: UsersRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    //iniciar sesión en FirebaseAuth
    override suspend fun login(email: String, password: String) =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Outcome.Completed
        } catch (e: Exception){
            e.printStackTrace()
            when(e) {
                is com.google.firebase.auth.FirebaseAuthInvalidUserException ->
                    Outcome.Error(ErrorDescription.UserNotFound)
                is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException ->
                    Outcome.Error(ErrorDescription.UserWrongCredentials)
                is com.google.firebase.FirebaseTooManyRequestsException ->
                    Outcome.Error(ErrorDescription.ServerUnavailable)
                else -> Outcome.Error(ErrorDescription.UnknownError)
            }
        }

}