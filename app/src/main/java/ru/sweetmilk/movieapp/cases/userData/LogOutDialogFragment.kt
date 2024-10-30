package ru.sweetmilk.movieapp.cases.userData

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.sweetmilk.movieapp.R

class LogOutDialogFragment(
    private val onLogOutCallback: () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.log_out_dialog_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                onLogOutCallback()
            }
            .setNegativeButton(getString(R.string.no), null)
            .create()
    }

    companion object {
        val TAG = "LogOutDialogFragment"
    }
}