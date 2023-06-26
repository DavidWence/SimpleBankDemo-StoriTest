package com.example.simplebankingapp_storitest.data.repositories

import com.example.simplebankingapp_storitest.data.utils.database.UserInfoSnapshot
import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.asOutcome
import com.example.simplebankingapp_storitest.domain.repositories.UsersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class FirebaseUsersRepository(private val auth: FirebaseAuth,
                              private val database: DatabaseReference): UsersRepository {

    override suspend fun signIn(email: String, password: String) =
        try {
            //se inicia la sesión
            auth.signInWithEmailAndPassword(email, password).await()
            //se obtienen los datos del usuario
            (database.child(DatabaseIDs.TABLE_USER_DATA)
                .child(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                .get().await().getValue(UserInfoSnapshot::class.java))?.toUserInfo()
                .asOutcome(ErrorDescription.UserNotFound)
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

    override suspend fun validateEmail(email: String) =
        try {
            //si el usuario ya tiene métodos de registro por email significa que ya está registrado
            val withRegistration =
                ((auth.fetchSignInMethodsForEmail(email).await().signInMethods?.size ?: 0) > 0)
            if(withRegistration)
                Outcome.Error(ErrorDescription.UserAlreadyRegistered)
            else
                Outcome.Completed
        } catch (e: Exception){
            e.printStackTrace()
            Outcome.Error(ErrorDescription.UnknownError)
        }

}