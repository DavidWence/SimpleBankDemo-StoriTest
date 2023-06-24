package com.example.simplebankingapp_storitest.data.repositories

import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.asOutcome
import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RealtimeUserInfoRepository: UserInfoRepository {

    private var database = Firebase.database.reference
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun getBalance() =
        try {
            (database.child(DatabaseIDs.TABLE_USER_DATA)
                .child(auth.currentUser?.uid ?: "")
                .child(DatabaseIDs.FIELD_BALANCE)
                .get().await().getValue(Double::class.java))
                .asOutcome()
        } catch (e: Exception){
            e.printStackTrace()
            Outcome.Error(ErrorDescription.UnknownError)
        }

}