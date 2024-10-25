package ru.sweetmilk.movieapp.validation.constraints

import ru.sweetmilk.movieapp.validation.ValidationError

data object NotEmptyConstraint : Constraint() {

    init {
        putTypedValidationCallback(this::validateString)
    }

    private fun validateString(string: String?): ValidationError? {
        return if (string != null && string.isEmpty())
            ValidationError.Empty
        else
            null
    }
}