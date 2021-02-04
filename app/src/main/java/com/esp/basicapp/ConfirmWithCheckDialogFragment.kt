package com.esp.basicapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class ConfirmWithCheckDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_MESSAGE = "message"

        fun makeDialog(message: String): Fragment {
            return ConfirmWithCheckDialogFragment().apply {
                arguments = bundleOf(
                    ARG_MESSAGE to message
                )
            }
        }
    }

    interface DialogResultListener {
        fun onSave()
        fun onCancel()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.confirm_with_check_dialog)
            .setPositiveButton("削除") { dialog, state ->
                onSave()
            }
            .setNegativeButton("キャンセル") { dialog, state ->
                onCancel()
            }
            .create()
    }

    private fun onSave() {
        val listener = parentFragment as? DialogResultListener
            ?: activity as? DialogResultListener

        listener?.onSave()
    }

    private fun onCancel() {
        val listener = parentFragment as? DialogResultListener
            ?: activity as? DialogResultListener

        listener?.onCancel()
    }
}
