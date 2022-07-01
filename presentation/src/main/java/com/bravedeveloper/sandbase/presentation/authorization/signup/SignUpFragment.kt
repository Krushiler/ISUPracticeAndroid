package com.bravedeveloper.sandbase.presentation.authorization.signup

import android.app.AlertDialog
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import com.bravedeveloper.domain.model.user.registration.SignUpInput
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.DialogTemporaryPasswordBinding
import com.bravedeveloper.sandbase.databinding.FragmentSignUpBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.fullnamedittext.FullNameEditTextUtil
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneEditTextUtil
import com.bravedeveloper.sandbase.presentation.base.phoneedittext.PhoneTextWatcher
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        binding.apply {
            val context = requireContext()
            val normalTextFont = ResourcesCompat.getFont(context, R.font.weblysleekuil)
            val boldTextFont = ResourcesCompat.getFont(context, R.font.weblysleekuisb)

            var pickedRole = UserRoleEnum.SELLER

            customerLayout.setOnClickListener {
                pickedRole = UserRoleEnum.CUSTOMER
                vendorTextView.typeface = normalTextFont
                vendorCheckboxImage.setBackgroundResource(R.drawable.inactive_ring_shape)
                customerTextView.typeface = boldTextFont
                customerCheckboxImage.setBackgroundResource(R.drawable.active_ring_shape)
            }
            vendorLayout.setOnClickListener {
                pickedRole = UserRoleEnum.SELLER
                customerTextView.typeface = normalTextFont
                customerCheckboxImage.setBackgroundResource(R.drawable.inactive_ring_shape)
                vendorTextView.typeface = boldTextFont
                vendorCheckboxImage.setBackgroundResource(R.drawable.active_ring_shape)
            }

            privacyPolicyText.movementMethod = LinkMovementMethod.getInstance()
            phoneEditText.addTextChangedListener(PhoneTextWatcher(phoneEditText))

            signUpButton.setOnClickListener {
                checkFullNameEditText()
                checkPhoneEditText()
                if (!phoneTextInputLayout.isErrorEnabled and !fullNameInputLayout.isErrorEnabled) {
                    userViewModel.signUp(
                        SignUpInput(
                            name = fullNameEditText.text.toString(),
                            phone = phoneEditText.text.toString(),
                            role = pickedRole,
                            cityId = 0,
                            acceptPrivacy = true, shouldSendCode = true
                        )
                    )
                    initAlertDialog()
                }
            }
            fullNameEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) checkFullNameEditText()

            }
            phoneEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) checkPhoneEditText()
            }
        }
    }

    private fun initAlertDialog() {
        binding.apply {
            val dialogSignUp =
                DialogTemporaryPasswordBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogSignUp.root)
                .create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialogSignUp.closeImg.setOnClickListener {
                dialog.dismiss()
            }
            userViewModel.getSignUp().observe(viewLifecycleOwner) {
                if (it) dialog.show()
            }

        }
    }

    private fun checkFullNameEditText() {
        binding.apply {
            FullNameEditTextUtil.checkAndSet(
                fullNameInputLayout,
                fullNameEditText,
                getString(R.string.must_be_filled),
                getString(R.string.incorrect_data)
            )
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
