package ru.sweetmilk.movieapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


object SnackBarUtil {
    fun showSnackBar(v: View?, snackBarText: String?) {
        if (v == null || snackBarText.isNullOrEmpty()) {
            return
        }
        Snackbar.make(v, snackBarText, Snackbar.LENGTH_LONG).show()
    }
}