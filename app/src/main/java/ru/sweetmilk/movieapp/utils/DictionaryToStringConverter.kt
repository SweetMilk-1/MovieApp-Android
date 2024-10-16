package ru.sweetmilk.movieapp.utils

import ru.sweetmilk.movieapp.api.models.DictionaryItem

object DictionaryToStringConverter {
    fun convert(dictionaryItems: List<DictionaryItem>?): String? {
        return dictionaryItems?.joinToString(transform = { it.name })?.lowercase()
    }
}