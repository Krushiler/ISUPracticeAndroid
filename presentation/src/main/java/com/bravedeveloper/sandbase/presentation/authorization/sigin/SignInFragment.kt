package com.bravedeveloper.sandbase.presentation.authorization.sigin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.domain.model.user.authorization.SignInInput
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.FragmentSignInBinding
import com.bravedeveloper.sandbase.presentation.authorization.AuthorizationViewModel
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneEditTextUtil
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneTextWatcher
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseBindingFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val viewModel: AuthorizationViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        userViewModel.getPhone().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.phoneEditText.setText(it)
                binding.passwordEditTextLayout.setHint(R.string.password_from_sms)
                checkPhoneEditText()
            }
        }
    }

    private fun initViews() {
        binding.apply {
            phoneEditText.addTextChangedListener(PhoneTextWatcher(phoneEditText))
            signUpButton.setOnClickListener {
                checkPhoneEditText()
                if (!phoneTextInputLayout.isErrorEnabled && passwordEditText.text?.isNotEmpty() == true)
                    userViewModel.signIn(
                        SignInInput(
                            phoneEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                    )
            }
            forgotPasswordButton.setOnClickListener {
                viewModel.goToForgotPasswordScreen()
                it.hideKeyboard(context)

            }
            phoneEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) checkPhoneEditText()
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