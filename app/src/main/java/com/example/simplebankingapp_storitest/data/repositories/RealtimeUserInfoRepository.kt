package com.example.simplebankingapp_storitest.data.repositories

import com.example.simplebankingapp_storitest.data.utils.definitions.Preference_LocalUserInfo
import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome
import com.example.simplebankingapp_storitest.domain.entities.asOutcome
import com.example.simplebankingapp_storitest.domain.entities.outcomeCompleted
import com.example.simplebankingapp_storitest.domain.entities.user.UserInfo
import com.example.simplebankingapp_storitest.domain.repositories.UserInfoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class RealtimeUserInfoRepository(private val userPrefs: Preference_LocalUserInfo,
                                 private val database: DatabaseReference):
    UserInfoRepository {
    override fun setUserInfo(userInfo: UserInfo) = outcomeCompleted {
        userPrefs.putEmail(userInfo.email)
        userPrefs.putName(userInfo.name)
        userPrefs.putSurname(userInfo.surname)
    }

    override suspend fun getBalance() =
        try {
            //se intenta obtener la información mas reciente
            (database.child(DatabaseIDs.TABLE_USER_DATA)
                .child(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                .child(DatabaseIDs.FIELD_BALANCE)
                .get().await().getValue(Double::class.java))
                .asOutcome()
        } catch (e: Exception){
            e.printStackTrace()
            Outcome.Error(ErrorDescription.UnknownError)
        }

    override fun getName() = userPrefs.name.asOutcome(ErrorDescription.MissingValueError)

    override fun getSurname() = userPrefs.surname.asOutcome(ErrorDescription.MissingValueError)

    override fun getEmail() = userPrefs.email.asOutcome(ErrorDescription.MissingValueError)

}