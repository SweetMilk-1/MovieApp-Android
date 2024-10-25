package ru.sweetmilk.movieapp.validation.constraints

import ru.sweetmilk.movieapp.validation.ValidationError

class MaxLengthConstraint(private val maxLength: Int) : Constraint() {
    init {
        putTypedValidationCallback(this::validateString)
    }

    private fun validateString(str: String?): ValidationError? {
        return if (str != null && str.length < maxLength)
            ValidationError.MoreThenMaxLength(
                str.length,
                maxLength
            )
        else null
    }
}