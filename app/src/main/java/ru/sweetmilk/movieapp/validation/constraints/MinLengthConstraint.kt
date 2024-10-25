package ru.sweetmilk.movieapp.validation.constraints

import ru.sweetmilk.movieapp.validation.ValidationError

class MinLengthConstraint(private val minLength: Int) : Constraint() {

    init {
        putTypedValidationCallback(this::validateString)
    }

    private fun validateString(str: String?): ValidationError? {
        return if (str != null && str.length < minLength)
            ValidationError.LessThenMinLength(
                str.length,
                minLength
            )
        else null
    }
}