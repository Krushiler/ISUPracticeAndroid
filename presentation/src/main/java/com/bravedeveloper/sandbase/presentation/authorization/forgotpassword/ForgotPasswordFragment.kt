package com.bravedeveloper.sandbase.presentation.authorization.forgotpassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentForgotPasswordBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneEditTextUtil
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneTextWatcher
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseBindingFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        userViewModel.getPhone().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.phoneEditText.setText(it)
            }
        }
    }

    private fun initViews() {
        binding.apply {
            includedToolbar.toolbar.setNavigationIcon(R.drawable.ic_backspace)
            includedToolbar.toolbar.setNavigationOnClickListener {
                it.hideKeyboard(context)
                requireActivity().onBackPressed()
            }
            phoneEditText.addTextChangedListener(PhoneTextWatcher(phoneEditText))
            resetPasswordButton.setOnClickListener {
                checkPhoneEditText()
                if (!phoneTextInputLayout.isErrorEnabled) {
                    userViewModel.resetPassword(phoneEditText.text.toString())
                    userViewModel.goBackAfterReset()
                }
            }
            forgotPasswordLayout.setOnClickListener {
                it.hideKeyboard(context)
            }
        }
    }
    private fun checkPhoneEditText() {
        binding.apply {
            PhoneEditTextUtil.setAndCheckPhoneErrorState(
                phoneTextInputLayout,
                phoneEditText,
                getString(R.string.must_be_filled),
                getString(R.string.invalid_number)
            )
        }
    }

}