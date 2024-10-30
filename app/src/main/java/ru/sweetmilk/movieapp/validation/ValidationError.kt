package ru.sweetmilk.movieapp.validation

sealed class ValidationError {
    data object Null : ValidationError()
    data object Empty : ValidationError()
    class LessThenMinLength(val length: Int, val minLength: Int) : ValidationError()
    class MoreThenMaxLength(val length: Int, val minLength: Int) : ValidationError()
    data object NotEmail : ValidationError()
}