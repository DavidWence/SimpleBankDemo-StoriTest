package com.example.simplebankingapp_storitest.domain.usecases.registry

import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome

class ValidateRepeatPassword {

    operator fun invoke(password: String, repeatPassword: String) =
        when {
            password.isEmpty() -> Outcome.Error(ErrorDescription.RepeatPasswordEmpty)
            password != repeatPassword -> Outcome.Error(ErrorDescription.RepeatPasswordNotEqual)
            else -> Outcome.Completed
        }

}