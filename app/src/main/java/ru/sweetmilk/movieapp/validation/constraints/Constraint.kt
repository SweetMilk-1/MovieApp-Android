package ru.sweetmilk.movieapp.validation.constraints

import ru.sweetmilk.movieapp.validation.ValidationError
import kotlin.reflect.KClass

typealias TypeValidationMap = MutableMap<KClass<*>, (value: Any?) -> ValidationError?>

class ValidationException(message: String) : Exception(message)

abstract class Constraint {
    protected val typeValidationMap: TypeValidationMap = mutableMapOf()

    open fun validate(value: Any?): ValidationError? {
        if (value == null)
            return null

        val validator = typeValidationMap[value::class]
        if (validator == null) {
            val thisClassName = this::class.java.name
            val valueTypeName = value::class.java.name
            throw ValidationException("Could not validate value with type $valueTypeName in constraint $thisClassName")
        }

        return validator.invoke(value)
    }

    protected inline fun <reified T> putTypedValidationCallback(
        noinline callback: (value: T?) -> ValidationError?
    ) {
        @Suppress("UNCHECKED_CAST")
        typeValidationMap[T::class] = callback as (value: Any?) -> ValidationError?
    }
}