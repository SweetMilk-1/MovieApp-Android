package ru.sweetmilk.movieapp.validation

import ru.sweetmilk.movieapp.validation.constraints.Constraint

object ConstraintHandler {
    fun handle(value: Any?, constraints: Array<Constraint>?): ValidationError? {
        if (constraints != null) {
            for (constraint  in constraints) {
                val error = constraint.validate(value)
                if (error != null)
                    return error
            }
        }
        return null
    }
}