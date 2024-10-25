package ru.sweetmilk.movieapp.validation.constraints

import ru.sweetmilk.movieapp.validation.ValidationError

object NotNullConstraint: Constraint() {
    override fun validate(value: Any?): ValidationError? {
        return  if (value == null) ValidationError.Null else null
    }
}