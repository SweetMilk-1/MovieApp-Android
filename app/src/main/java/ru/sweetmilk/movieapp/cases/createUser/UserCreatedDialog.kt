package ru.sweetmilk.movieapp.cases.createUser

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.sweetmilk.movieapp.R

class UserCreatedDialog(private val callback: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.user_created_message))
            .setPositiveButton(android.R.string.ok, ) {_, _ -> callback()}
            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        callback()
    }

    companion object {
        val TAG = "UserCreatedDialog"
    }
}