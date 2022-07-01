package com.bravedeveloper.sandbase.presentation.base.fullnamedittext

import com.bravedeveloper.sandbase.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object FullNameEditTextUtil {
    fun checkAndSet(fullNameInputLayout: TextInputLayout, fullNameEditText: TextInputEditText, emptyString: String, incorrectString: String) {
        if (fullNameEditText.text.toString().isEmpty()) {
            fullNameInputLayout.error = emptyString
            fullNameInputLayout.isErrorEnabled = true
        } else if (fullNameEditText.text.toString().trim().isEmpty()) {
            fullNameInputLayout.error = incorrectString
            fullNameInputLayout.isErrorEnabled = true
        } else {
            fullNameInputLayout.isErrorEnabled = false
        }
    }
}