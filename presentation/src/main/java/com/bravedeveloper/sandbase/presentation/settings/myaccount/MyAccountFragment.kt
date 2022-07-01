package com.bravedeveloper.sandbase.presentation.settings.myaccount

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.bravedeveloper.data.remote.api.SandBaseApi
import com.bravedeveloper.domain.model.user.password.ChangePasswordInput
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileInput
import com.bravedeveloper.sandbase.R
import com.bravedeveloper.sandbase.databinding.AlertDialogDeletingAccountBinding
import com.bravedeveloper.sandbase.databinding.DialogPickImageBinding
import com.bravedeveloper.sandbase.databinding.FragmentMyAccountBinding
import com.bravedeveloper.sandbase.presentation.base.BaseBindingFragment
import com.bravedeveloper.sandbase.presentation.base.views.InfoItemView
import com.bravedeveloper.sandbase.presentation.global.UserViewModel
import com.bravedeveloper.sandbase.presentation.settings.SettingsViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry
import com.bravedeveloper.sandbase.presentation.settings.general.EditState
import com.bravedeveloper.sandbase.util.hideKeyboard
import com.bravedeveloper.sandbase.util.makeToast

@AndroidEntryPoint
class MyAccountFragment :
    BaseBindingFragment<FragmentMyAccountBinding>(FragmentMyAccountBinding::inflate) {

    companion object {
        private const val AVATAR_DATA = "data"
    }

    private val viewModel: SettingsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private var lastEmail: String? = ""
    private var lastSurname: String? = ""
    private var lastPhone: String? = ""
    private var newAvatar: Bitmap? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.lastPassword()
        userViewModel.getMe()
        binding.apply {
            userViewModel.getUser().observe(viewLifecycleOwner) {
                if (it != null) {
                    surnameNameItem.setDescription(it.name)
                    emailItem.setDescription(it.email)
                    phoneItem.setDescription(it.phone)
                    phoneItem.setPhoneNumberTextWatcher()
                    lastEmail = it.email
                    lastSurname = it.name
                    lastPhone = it.phone
                    if (it.lastDayReplyCount != null && it.replyPerDayLimit != null) {
                        setLimitProgress(it.lastDayReplyCount!!, it.replyPerDayLimit!!)
                    }
                    Glide.with(requireContext())
                        .load(SandBaseApi.BASE_URL + it.avatar?.formats?.large?.url)
                        .centerCrop()
                        .into(imageAvatar)
                } else {
                    viewModel.goToAuthorizationScreen()
                }
            }
            userViewModel.getLastPassword().observe(viewLifecycleOwner) {
                passwordItem.setDescription(
                    "${requireContext().getString(R.string.password_changed)} $it ${
                        requireContext().getString(R.string.days_ago)
                    }"
                )
            }

            val takePhotoResultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == RESULT_OK && it.data != null) {
                        val bundle = it.data?.extras
                        val bitmap = bundle?.get(AVATAR_DATA) as Bitmap
                        blurImageAvatar.setImageDrawable(null)
                        newAvatar = bitmap
                        setBlur(bitmap, blurImageAvatar)
                    }
                }

            val pickPhotoGalleryResultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == RESULT_OK && it.data != null) {
                        val inputStream =
                            requireActivity().contentResolver.openInputStream(it.data?.data!!)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        blurImageAvatar.setImageDrawable(null)
                        newAvatar = bitmap
                        setBlur(bitmap, blurImageAvatar)
                    }
                }

            changeAvatarIcon.setOnClickListener {
                val dialogPickImage = DialogPickImageBinding.inflate(layoutInflater)
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogPickImage.root)
                    .create()

                dialogPickImage.choosePhotoCancel.setOnClickListener {
                    dialog.dismiss()
                }

                dialogPickImage.choosePhotoGallery.setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    pickPhotoGalleryResultLauncher.launch(intent)
                    dialog.dismiss()
                }

                dialogPickImage.choosePhotoCamera.setOnClickListener {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    takePhotoResultLauncher.launch(intent)
                    dialog.dismiss()
                }

                dialog.show()

            }

        }

        viewModel.getEditState().observe(viewLifecycleOwner) { editable ->
            if (editable == EditState.Editing) {
                setEditable()
            } else {
                setNonEditable()
                binding.apply {
                    if (editable == EditState.Saved) {
                        if (newPasswordEt.text.toString() == repeatPasswordEt.text.toString()
                        ) {
                            userViewModel.changePassword(
                                ChangePasswordInput(
                                    currentPasswordEt.text.toString(),
                                    newPasswordEt.text.toString(),
                                    repeatPasswordEt.text.toString()
                                )
                            )
                        }
                        showEmailConfirm()
                        lastEmail = emailItem.getDescription()
                        lastSurname = surnameNameItem.getDescription()
                        lastPhone = phoneItem.getDescription()
                        userViewModel.updateProfile(
                            UpdateProfileInput(
                                surnameNameItem.getDescription(),
                                emailItem.getDescription(),
                                0
                            )
                        )
                        newAvatar?.let { uploadAvatar(it) }
                    } else if (editable == EditState.Canceled) {
                        emailItem.setDescription(lastEmail)
                        surnameNameItem.setDescription(lastSurname)
                        phoneItem.setDescription(lastPhone)
                    }
                }
            }
        }
        initAlertDialog()
    }

    private fun uploadAvatar(image: Bitmap) {
        userViewModel.uploadAvatar(image)
    }

    private fun initAlertDialog() {
        binding.apply {
            val dialogDeletingAccount =
                AlertDialogDeletingAccountBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogDeletingAccount.root)
                .create()

            dialogDeletingAccount.root.setOnClickListener {
                dialogDeletingAccount.codeEditText.clearFocus()
            }

            dialogDeletingAccount.cancellationCrossImageButton.setOnClickListener {
                dialogDeletingAccount.codeEditText.clearFocus()
                hideKeyboard(requireActivity())
                dialog.dismiss()
            }

            dialogDeletingAccount.confirmationDeletingResponseButton.setOnClickListener {
                userViewModel.deleteAccount(dialogDeletingAccount.codeEditText.text.toString(),
                    onSuccess = {
                        makeToast(requireContext(), R.string.account_deleted)
                        dialog.dismiss()
                    }, onWrongCode = {
                        makeToast(requireContext(), R.string.wrong_code)
                    }, onFail = {
                        makeToast(requireContext(), R.string.error_occurred)
                    })
                hideKeyboard(requireActivity())
            }

            deleteAccount.setOnClickListener {
                dialog.show()
                userViewModel.getDeleteOwnAccountVerificationSmsCode()
            }
        }
    }

    private fun setLimitProgress(progress: Int, max: Int) {
        binding.apply {
            offerLimitCountTv.text = context?.getString(R.string.responses_count, progress, max)
        }
    }

    private fun setBlur(fromImage: Bitmap?, toView: ImageView) {
        if (toView.drawable == null) {
            Blurry
                .with(requireContext())
                .radius(4)
                .sampling(4)
                .from(fromImage)
                .into(toView)
        }
    }

    private fun setEditable() {
        binding.apply {
            changeAvatarIcon.visibility = View.VISIBLE
            editablePasswordLayout.visibility = View.VISIBLE
            deleteAccount.visibility = View.VISIBLE
            if (imageAvatar.drawable != null) setBlur((imageAvatar.drawable as? BitmapDrawable)?.bitmap, blurImageAvatar)
            blurImageAvatar.visibility = View.VISIBLE
            setEditableInfoFields(true, listOf(surnameNameItem, emailItem))
            setDisabledStyle(false, listOf(surnameNameItem, emailItem))
            passwordLayout.visibility = View.GONE
            offerLimitCardView.visibility = View.GONE
            createOfferCheckbox.visibility = View.GONE
        }
    }

    private fun setNonEditable() {
        binding.apply {
            changeAvatarIcon.visibility = View.GONE
            editablePasswordLayout.visibility = View.GONE
            deleteAccount.visibility = View.GONE
            blurImageAvatar.visibility = View.GONE
            blurImageAvatar.setImageDrawable(null)
            setEditableInfoFields(false, listOf(surnameNameItem, emailItem))
            setDisabledStyle(true, listOf(surnameNameItem, emailItem, phoneItem))
            passwordLayout.visibility = View.VISIBLE
            offerLimitCardView.visibility = View.VISIBLE
            createOfferCheckbox.visibility = View.VISIBLE
        }
    }

    private fun showEmailConfirm() {
        binding.apply {
            forEmailTv.visibility =
                if (lastEmail != emailItem.getDescription()) View.VISIBLE else View.GONE
        }
    }

    private fun setEditableInfoFields(isEditable: Boolean, infoViewList: List<InfoItemView>) {
        for (infoView in infoViewList) {
            infoView.setEditable(isEditable)
        }
    }

    private fun setDisabledStyle(isDisabled: Boolean, infoViewList: List<InfoItemView>) {
        for (infoView in infoViewList) {
            infoView.setDisabledStyle(isDisabled)
        }
    }
}