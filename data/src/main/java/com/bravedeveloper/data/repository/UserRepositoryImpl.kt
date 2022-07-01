package com.bravedeveloper.data.repository

import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import com.bravedeveloper.data.remote.api.SandBaseService
import com.bravedeveloper.data.remote.api.request.Request
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
import com.bravedeveloper.domain.repository.UserRepository
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class UserRepositoryImpl(
    private val api: SandBaseService
) : UserRepository {

    override fun signIn(input: SignInInput): Single<ResponseData<SignInData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.signIn(input))
        return api.signIn(paramObject.toString())
    }

    override fun signUp(input: SignUpInput): Single<ResponseData<SignUpData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.signUp(input))
        return api.signUp(paramObject.toString())
    }

    override fun updateProfile(input: UpdateProfileInput): Single<ResponseData<UpdateProfileData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.updateProfile(input))
        return api.updateProfile(paramObject.toString())
    }

    override fun changePassword(input: ChangePasswordInput): Single<ResponseData<ChangePasswordData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.changePassword(input))
        return api.changePassword(paramObject.toString())
    }

    override fun lastPasswordUpdate(): Single<ResponseData<LastPasswordData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.lastUpdatePassword())
        return api.lastPasswordUpdate(paramObject.toString())
    }

    override fun deleteOwnAccount(input: DeleteOwnAccountInput): Single<ResponseData<DeleteOwnAccountData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.deleteOwnAccount(input))
        return api.deleteOwnAccount(paramObject.toString())
    }

    override fun getVerificationSmsCode(input: VerificationSmsCodeInput): Single<ResponseData<VerificationSmsCodeData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getVerificationSmsCode(input))
        return api.getVerificationSmsCode(paramObject.toString())
    }

    override fun resetPassword(input: ResetPasswordInput): Single<ResponseData<ResetPasswordData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.resetPassword(input))
        return api.resetPassword(paramObject.toString())
    }

    override fun getMe(): Single<ResponseData<MeData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getMe())
        return api.getMe(paramObject.toString())
    }

    override fun removeAvatar(): Single<ResponseData<RemoveAvatarData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.removeAvatar())
        return api.removeAvatar(paramObject.toString())
    }

    override fun getNotifications(input: NotificationsInput): Single<ResponseData<NotificationsData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getNotifications(input))
        return api.getNotifications(paramObject.toString())
    }

    override fun markAsRead(input: MarkAsReadInput): Single<ResponseData<MarkAsReadData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.markRepliesAsRead(input))
        return api.markAsRead(paramObject.toString())
    }

    override fun uploadAvatar(image: Bitmap): Single<ResponseData<UploadAvatarData>> {
        val fileName = "file"

        val paramObject = JSONObject()
        paramObject.put("query", Request.uploadAvatar(fileName))

        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        val imageByteArray = stream.toByteArray()

        val mapBody =  "{\"0\":[\"variables.$fileName\"]}"

        val body = MultipartBody.Part.createFormData(
            "0",
            "$fileName.jpeg",
            imageByteArray.toRequestBody("image/jpeg".toMediaType())
        )

        return api.uploadAvatar(
            paramObject.toString(),
            mapBody,
            body
        )

    }

    override fun upsertFCMTokenUseCase(input: UpsertFCMTokenInput): Single<ResponseData<UpsertFCMTokenData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.upsertFCMToken(input))
        return api.upsertFCMToken(paramObject.toString())
    }

    override fun rateUser(input: RateInput): Single<ResponseData<RateUserData>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.rateUser(input = input))
        return api.rateUser(paramObject.toString())
    }
}