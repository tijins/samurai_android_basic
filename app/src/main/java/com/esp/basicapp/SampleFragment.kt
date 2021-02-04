package com.esp.basicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_sample.btn_ask
import kotlinx.android.synthetic.main.fragment_sample.btn_ask_with_check
import kotlinx.android.synthetic.main.fragment_sample.btn_save
import kotlinx.android.synthetic.main.fragment_sample.txt_name

class SampleFragment : Fragment(),
    ConfirmDialogFragment.DialogResultListener {
    companion object {
        private const val ARG_NAME = "name"

        @JvmStatic
        fun newInstance(name: String): Fragment {
            return SampleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
        }
    }

    interface SampleFragmentListener {
        fun onSave(name: String)
    }

    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_name.setText(name)

        btn_save.setOnClickListener {
            // Fragmentの入れ子に対応する
            val listener = parentFragment as? SampleFragmentListener
                ?: activity as? SampleFragmentListener
            listener?.onSave(txt_name.text.toString())
        }

        btn_ask.setOnClickListener {
            val dialog = ConfirmDialogFragment.makeDialog("保存しますか？")
            childFragmentManager
                .beginTransaction()
                .add(dialog, ConfirmDialogFragment::class.java.simpleName)
                .commit()
        }

        btn_ask_with_check.setOnClickListener {
            val dialog = ConfirmWithCheckDialogFragment.makeDialog("削除しますか？")
            childFragmentManager
                .beginTransaction()
                .add(dialog, ConfirmDialogFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onSave() {
        // Fragmentの入れ子に対応する
        val listener = parentFragment as? SampleFragmentListener ?: activity as? SampleFragmentListener
        listener?.onSave(txt_name.text.toString())
    }

    override fun onCancel() {
        //なにもしない
    }
}
