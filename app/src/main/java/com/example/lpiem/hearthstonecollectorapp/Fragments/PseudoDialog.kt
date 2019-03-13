package com.example.lpiem.hearthstonecollectorapp.Fragments

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.lpiem.hearthstonecollectorapp.R

class PseudoDialog : DialogFragment() {
    companion object {

        private const val TAG = "PseudoDialog"

        private const val EXTRA_TITLE = "titre"
        private const val EXTRA_HINT = "hint"
        private const val EXTRA_MULTILINE = "multiline"
        private const val EXTRA_TEXT = "text"

        fun newInstance(title: String? = null, hint: String? = null, text: String? = null, isMultiline: Boolean = false) : PseudoDialog {
            val dialog = PseudoDialog()
            val args = Bundle().apply {
                putString(EXTRA_TITLE, title)
                putString(EXTRA_HINT, hint)
                putString(EXTRA_TEXT, text)
                putBoolean(EXTRA_MULTILINE, isMultiline)
            }
            dialog.arguments = args
            return dialog
        }
    }

    lateinit var editText: EditText
    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString(EXTRA_TITLE)
        val hint = arguments?.getString(EXTRA_HINT)
        val text: String? = arguments?.getString(EXTRA_TEXT)
        val isMultiline = arguments?.getBoolean(EXTRA_MULTILINE) ?: false

        // StackOverflowError
        // val view = layoutInflater.inflate(R.layout.dialog_edit_text, null)
        val view = activity!!.layoutInflater.inflate(R.layout.pseudo_dialog, null)

        editText = view.findViewById(R.id.editText)
        editText.hint = hint

        if (isMultiline) {
            editText.minLines = 3
            editText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        }
        if (text != null) {
            // editText.setText(text)
            // editText.setSelection(text.length)
            editText.append(text)
        }

        val builder = AlertDialog.Builder(context!!)
                .setTitle(title)
                .setView(view)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    onOk?.invoke()
                }
        val dialog = builder.create()

        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        return dialog
    }
}