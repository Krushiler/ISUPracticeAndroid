package com.bravedeveloper.sandbase.presentation.global

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.user.Me
import com.bravedeveloper.domain.model.user.authorization.SignInInput
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountInput
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenInput
import com.bravedeveloper.domain.model.user.password.ChangePasswordInput
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordInput
import com.bravedeveloper.domain.model.user.registration.SignUpInput
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileInput
import com.bravedeveloper.domain.model.user.verification.VerifiableActionsEnum
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeInput
import com.bravedeveloper.domain.usecase.*
import com.bravedeveloper.domain.usecase.notifications.fcm.UpsertFCMTokenUseCase
import com.bravedeveloper.domain.usecase.token.DeleteTokenUseCase
import com.bravedeveloper.domain.usecase.token.LoadTokenUseCase
import com.bravedeveloper.domain.usecase.token.SaveTokenUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signInSingleUseCase: SignInSingleUseCase,
    private val signUpSingleUseCase: SignUpSingleUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val changePasswordSingleUseCase: ChangePasswordSingleUseCase,
    private val lastPasswordSingleUseCase: LastPasswordSingleUseCase,
    private val getVerificationSmsCodeUseCase: GetVerificationSmsCodeUseCase,
    private val deleteOwnAccountUseCase: DeleteOwnAccountUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val removeAvatarUseCase: RemoveAvatarSingleUseCase,
    private val uploadAvatarUseCase: UploadAvatarSingleUseCase,
    private val user: User,
    private val router: Router,
    private val loadTokenUseCase: LoadTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val upsertFCMTokenUseCase: UpsertFCMTokenUseCase
) : BaseViewModel() {
    private val userLiveData = MutableLiveData<Me?>()
    private val tokenLiveData = MutableLiveData<String?>(null)
    private val phoneLiveData = MutableLiveData<String?>(null)
    private val lastPasswordLiveData = MutableLiveData<Long>(0)
    private val largeImageUrlLiveData = MutableLiveData<String?>(null)
    private val resetLiveData = MutableLiveData<Boolean>()
    private val signUpLiveData = MutableLiveData<Boolean>()

    init {
        checkToken()
    }

    fun getUserId() = user.user?.userId

    private fun updateUserLiveData() {
        userLiveData.value = user.user
    }

    private fun upsertFCMToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            upsertFCMTokenUseCase.saveInput(UpsertFCMTokenInput(token))
            upsertFCMTokenUseCase.execute()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    Log.d("FCM", "FCM refreshed token: $token")
                }, {
                    Log.e(it::class.simpleName, it.message.toString())
                })?.untilCleared()
        }
    }

    private fun checkToken() {
        loadTokenUseCase.execute()
        getMeUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.data?.me?.userId != null) {
                    user.user = it.data?.me
                    updateUserLiveData()
                    upsertFCMToken()
                } else {
                    user.user = null
                    updateUserLiveData()
                }
            }, {
                user.user = null
                updateUserLiveData()
                Log.e(it::class.simpleName, it.message.toString())
            }).untilCleared()
    }

    fun signIn(input: SignInInput) {
        signInSingleUseCase.saveInput(input)
        signInSingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { signInResponse ->
                   user.user = signInResponse.data?.signIn?.me
                    signInResponse.data?.signIn?.token?.let { saveToken(it) }
                    if (signInResponse.data?.signIn?.me != null) {
                        updateUserLiveData()
                        goToOrdersScreen()
                    }
                    upsertFCMToken()
                }, {
                    Log.e(it::class.simpleName, it.message.toString())
                }
            )?.untilCleared()
    }

    fun signUp(input: SignUpInput) {
        signUpSingleUseCase.saveInput(input)
        signUpSingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { signUpResponse ->
                    phoneLiveData.value = signUpResponse.data?.signUp?.record?.phone
                    signUpResponse.data?.signUp?.let { saveToken(it.recordId) }
                    signUpLiveData.value = true
                    upsertFCMToken()
                }, {
                    Log.e(it::class.simpleName, it.message.toString())
                }
            )?.untilCleared()
    }

    fun goAuthorizationAfterSignUp() {
        signUpLiveData.value = false
    }

    fun updateProfile(input: UpdateProfileInput) {
        if (checkUpdateInput(input)) {
            updateProfileUseCase.saveInput(input)
            updateProfileUseCase.execute()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    {
                        val user = userLiveData.value
                        user?.name = input.name
                        user?.email = input.email
                        this.user.user = user
                        updateUserLiveData()
                    }, {
                        Log.e(it::class.simpleName, it.message.toString())
                    }
                )?.untilCleared()
        }
    }

    fun lastPassword() {
        lastPasswordSingleUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { lastPasswordResponse ->
                    val currentDate = Date()
                    var lastDate = lastPasswordResponse.data?.lastPasswordUpdate
                    if (lastDate == null) lastDate = Date()
                    val daysAgo = (currentDate.time - lastDate.time) / (24 * 60 * 60 * 1000)
                    lastPasswordLiveData.value = daysAgo
                }, {
                    Log.e(it::class.simpleName, it.message.toString())
                }
            ).untilCleared()
    }

    fun signOut() {
        userLiveData.postValue(null)
        user.user = null
        destroyToken()
    }

    fun changePassword(input: ChangePasswordInput) {
        if (checkChangePasswordInput(input)) {
            changePasswordSingleUseCase.saveInput(input)
            changePasswordSingleUseCase.execute()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                    {}, {
                        Log.e(it::class.simpleName, it.message.toString())
                    }
                )?.untilCleared()
        }
    }

    fun getDeleteOwnAccountVerificationSmsCode() {
        getVerificationSmsCodeUseCase.saveInput(VerificationSmsCodeInput(VerifiableActionsEnum.DELETE_OWN_ACCOUNT))
        getVerificationSmsCodeUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({}, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun deleteAccount(
        input: String,
        onSuccess: () -> Unit,
        onWrongCode: () -> Unit,
        onFail: () -> Unit
    ) {
        deleteOwnAccountUseCase.saveInput(DeleteOwnAccountInput(input))
        deleteOwnAccountUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (it.data?.deleteOwnAccount == true) {
                    onSuccess()
                    signOut()
                } else {
                    onWrongCode()
                }
            }, {
                onFail()
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun resetPassword(input: String) {
        resetPasswordUseCase.saveInput(ResetPasswordInput(input, false))
        resetPasswordUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                resetLiveData.value = true
                phoneLiveData.value = input
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun goBackAfterReset() {
        resetLiveData.value = false
        router.exit()
    }

    fun getMe() {
        getMeUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                user.user = it.data?.me
                updateUserLiveData()
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            }).untilCleared()
    }

    fun uploadAvatar(image: Bitmap) {
        uploadAvatarUseCase.saveInput(image)
        uploadAvatarUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                user.user?.avatar = it.data?.uploadAvatar?.upload
                userLiveData.value = user.user
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun removeAvatar() {
        removeAvatarUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                user.user?.avatar = null
                userLiveData.value = user.user
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            }).untilCleared()
    }

    fun getLastPassword() = lastPasswordLiveData

    fun getUser(): LiveData<Me?> = userLiveData

    fun getToken() = tokenLiveData

    fun getPhone() = phoneLiveData

    fun getImage() = largeImageUrlLiveData

    fun getReset() = resetLiveData

    fun getSignUp() = signUpLiveData

    private fun checkChangePasswordInput(input: ChangePasswordInput): Boolean {
        if (input.password != "" && input.newPassword != "" && input.confirmPassword != "") {
            return true
        }
        return false
    }

    private fun checkUpdateInput(input: UpdateProfileInput): Boolean {
        val user = userLiveData.value
        if (user?.name == input.name && user.email == input.email) {
            return false
        }
        return true
    }

    private fun saveToken(token: String) {
        saveTokenUseCase.execute(token)
    }

    private fun destroyToken() {
        deleteTokenUseCase.execute()
    }

    private fun goToOrdersScreen() {
        router.newRootChain(Screens.orderListScreen())
    }

}