package com.bravedeveloper.sandbase.presentation.base.phoneedittext

import android.util.Log
import android.widget.EditText
import com.bravedeveloper.sandbase.presentation.base.views.errorabletinputlayout.ErrorableInputLayout
import com.google.android.material.textfield.TextInputLayout

object PhoneEditTextUtil {

    fun setAndCheckPhoneErrorState(phoneTextInputLayout: TextInputLayout, phoneEditText: EditText, emptyErrorText: String, incorrectText: String) {
        val text = phoneEditText.text?.filter { it.isDigit() }
            .toString()
        when {
            phoneEditText.text.toString().isEmpty() -> {
                phoneTextInputLayout.error = emptyErrorText
                phoneTextInputLayout.isErrorEnabled = true
            }
            text.length != 11 -> {
                phoneTextInputLayout.error = incorrectText
                phoneTextInputLayout.isErrorEnabled = true
            }
            text[1] !in listOf('3', '4', '7', '8', '9') -> {
                phoneTextInputLayout.error = incorrectText
                phoneTextInputLayout.isErrorEnabled = true
            }
            else -> {
                phoneTextInputLayout.isErrorEnabled = false
            }
        }
    }

}