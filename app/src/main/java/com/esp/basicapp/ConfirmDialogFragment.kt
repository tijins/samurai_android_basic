package com.esp.basicapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class ConfirmDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_MESSAGE = "message"

        fun makeDialog(message: String): Fragment {
            return ConfirmDialogFragment().apply {
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
        val message = arguments?.getString(ARG_MESSAGE)
        return AlertDialog.Builder(requireContext())
                .setTitle("タイトル")
                .setMessage(message)
                .setPositiveButton("保存") { dialog, state ->
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
