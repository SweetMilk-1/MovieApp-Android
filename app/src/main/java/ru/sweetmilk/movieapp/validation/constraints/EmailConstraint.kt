package ru.sweetmilk.movieapp.validation.constraints

import ru.sweetmilk.movieapp.validation.ValidationError

object EmailConstraint : Constraint() {
    init {
        putTypedValidationCallback(this::validateString)
    }

    private fun validateString(str: String?): ValidationError? {
        return if (str != null && !EMAIL_REGEXP.toRegex()
                .matches(str)
        ) ValidationError.NotEmail else null
    }
    
    private const val EMAIL_REGEXP = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+.[a-zA-Z0-9_-]+\$"
}