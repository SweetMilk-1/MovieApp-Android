package ru.sweetmilk.movieapp.validation

import ru.sweetmilk.movieapp.validation.constraints.Constraint

object Validator {
    private fun validate(value: Any?, constraints: Array<Constraint>?): ValidationError? {
        if (constraints != null) {
            for (constraint in constraints) {
                val error = constraint.validate(value)
                if (error != null)
                    return error
            }
        }
        return null
    }

    fun validate(
        valuesMap: Map<String, Any?>,
        constraintsMap: Map<String, Array<Constraint>?>
    ): Map<String, ValidationError> {
        val res = mutableMapOf<String, ValidationError>()
        for ((key, value) in valuesMap) {
            val constraints = constraintsMap[key]
            val validationError = validate(value, constraints)
            if (validationError != null)
                res[key] = validationError
        }
        return res
    }
}