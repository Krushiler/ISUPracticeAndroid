package com.bravedeveloper.sandbase.presentation.base.phoneedittext

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText


class PhoneTextWatcher(editText: TextInputEditText) : TextWatcher {
    private var sb = StringBuilder()
    private var ignore = false
    private val numPlace = 'X'
    private val text: TextInputEditText = editText
    private var oldPosition = text.selectionStart
    private var lengthPhone = text.length()

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        lengthPhone = text.length()
        oldPosition = text.selectionStart
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun afterTextChanged(editable: Editable) {
        if (!ignore) {
            removeFormat(editable.toString())
            applyFormat(sb.toString())
            ignore = true
            if (sb.length > 1) sb[1] = '7'
            editable.replace(0, editable.length, sb.toString())
            ignore = false
        }
        if ((lengthPhone - editable.length == 1 || lengthPhone - editable.length == 2) && oldPosition < editable.length)
            text.setSelection(oldPosition)
    }

    private fun removeFormat(text: String) {
        sb.setLength(0)
        for (i in 0 until text.length) {
            val c = text[i]
            if (isNumberChar(c)) {
                sb.append(c)
            }
        }
    }

    private fun applyFormat(text: String) {
        val template = getTemplate(text)
        sb.setLength(0)
        var i = 0
        var textIndex = 0
        while (i < template.length && textIndex < text.length) {
            if (template[i] == numPlace) {
                sb.append(text[textIndex])
                textIndex++
            } else {
                sb.append(template[i])
            }
            i++
        }
    }

    private fun isNumberChar(c: Char): Boolean {
        return c >= '0' && c <= '9'
    }

    private fun getTemplate(text: String): String {
        return if (text.startsWith("7")) {
            "+X (XXX) XXX-XX-XX"
        } else if (text.startsWith("8")) {
            "+X (XXX) XXX-XX-XX"
        } else "+7 (XXX) XXX-XX-XX"
    }
}