package com.bravedeveloper.domain.repository

import android.graphics.Bitmap
import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.MeData
import com.bravedeveloper.domain.model.user.authorization.SignInData
import com.bravedeveloper.domain.model.user.authorization.SignInInput
import com.bravedeveloper.domain.model.user.authorization.SignInResponse
import com.bravedeveloper.domain.model.user.avatar.removeAvatar.RemoveAvatarData
import com.bravedeveloper.domain.model.user.avatar.uploadavatar.UploadAvatarData
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountData
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountInput
import com.bravedeveloper.domain.model.user.notifications.NotificationsData
import com.bravedeveloper.domain.model.user.notifications.NotificationsInput
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenData
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenInput
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadData
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadInput
import com.bravedeveloper.domain.model.user.password.ChangePasswordData
import com.bravedeveloper.domain.model.user.password.ChangePasswordInput
import com.bravedeveloper.domain.model.user.password.ChangePasswordResponse
import com.bravedeveloper.domain.model.user.password.last.LastPasswordData
import com.bravedeveloper.domain.model.user.password.last.LastPasswordResponse
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordData
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordInput
import com.bravedeveloper.domain.model.user.rating.RateInput
import com.bravedeveloper.domain.model.user.rating.RateUserData
import com.bravedeveloper.domain.model.user.registration.SignUpData
import com.bravedeveloper.domain.model.user.registration.SignUpInput
import com.bravedeveloper.domain.model.user.registration.SignUpResponse
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileData
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileInput
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileResponse
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeData
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeInput
import io.reactivex.Single

interface UserRepository {

    fun signIn(input: SignInInput) : Single<ResponseData<SignInData>>

    fun signUp(input: SignUpInput) : Single<ResponseData<SignUpData>>

    fun updateProfile(input: UpdateProfileInput) : Single<ResponseData<UpdateProfileData>>

    fun changePassword(input: ChangePasswordInput) : Single<ResponseData<ChangePasswordData>>

    fun lastPasswordUpdate(): Single<ResponseData<LastPasswordData>>

    fun deleteOwnAccount(input : DeleteOwnAccountInput) : Single<ResponseData<DeleteOwnAccountData>>

    fun getVerificationSmsCode(input : VerificationSmsCodeInput) : Single<ResponseData<VerificationSmsCodeData>>

    fun resetPassword(input : ResetPasswordInput) : Single<ResponseData<ResetPasswordData>>

    fun getMe() : Single<ResponseData<MeData>>

    fun removeAvatar() : Single<ResponseData<RemoveAvatarData>>

    fun uploadAvatar(image: Bitmap) : Single<ResponseData<UploadAvatarData>>

    fun getNotifications(input: NotificationsInput) : Single<ResponseData<NotificationsData>>

    fun markAsRead(input: MarkAsReadInput) : Single<ResponseData<MarkAsReadData>>

    fun upsertFCMTokenUseCase(input: UpsertFCMTokenInput) : Single<ResponseData<UpsertFCMTokenData>>

    fun rateUser(input: RateInput): Single<ResponseData<RateUserData>>

}