package com.example.simplebankingapp_storitest.domain.usecases

import com.example.simplebankingapp_storitest.domain.entities.ErrorDescription
import com.example.simplebankingapp_storitest.domain.entities.Outcome

object UserInfoUtils {
    val REGEX_EMAIL = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
    val REGEX_NAME = Regex("^[\\p{L} .'-]+\$")
}

class ValidateName {
    operator fun invoke(name: String,
                        onEmptyError: ErrorDescription,
                        onMismatchError: ErrorDescription) =
        if(name.isEmpty())
            Outcome.Error(onEmptyError)
        else if(!UserInfoUtils.REGEX_NAME.matches(name))
            Outcome.Error(onMismatchError)
        else
            Outcome.Completed
}
class ValidateEmail {
    operator fun invoke(email: String) =
        if(email.isEmpty())
            Outcome.Error(ErrorDescription.EmailEmpty)
        else if(!UserInfoUtils.REGEX_EMAIL.matches(email))
            Outcome.Error(ErrorDescription.EmailRegexMismatch)
        else
            Outcome.Completed
}

class ValidatePassword {
    operator fun invoke(password: String, validateMinimumLength: Boolean = false) =
        if (password.isEmpty())
            Outcome.Error(ErrorDescription.PasswordEmpty)
        else if (validateMinimumLength && password.length < 8)
            Outcome.Error(ErrorDescription.PasswordNotMinimumLenght)
        else
            Outcome.Completed
}