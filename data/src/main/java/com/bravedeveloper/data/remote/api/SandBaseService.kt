package com.bravedeveloper.data.remote.api

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.CitiesPagination
import com.bravedeveloper.domain.model.city.cargo.CargoData
import com.bravedeveloper.domain.model.city.order.approvereply.ApproveReplyData
import com.bravedeveloper.domain.model.city.order.getorder.OrderData
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateData
import com.bravedeveloper.domain.model.city.order.removeorder.RemoveOrdersData
import com.bravedeveloper.domain.model.city.order.search.OrdersData
import com.bravedeveloper.domain.model.city.order.seller.createreply.CreateReplyData
import com.bravedeveloper.domain.model.city.order.seller.removereply.RemoveReplyData
import com.bravedeveloper.domain.model.city.order.unauthorized.OrdersUnauthorizedData
import com.bravedeveloper.domain.model.user.MeData
import com.bravedeveloper.domain.model.user.authorization.SignInData
import com.bravedeveloper.domain.model.user.authorization.SignInResponse
import com.bravedeveloper.domain.model.user.avatar.removeAvatar.RemoveAvatarData
import com.bravedeveloper.domain.model.user.avatar.uploadavatar.UploadAvatarData
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountData
import com.bravedeveloper.domain.model.user.notifications.NotificationsData
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenData
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadData
import com.bravedeveloper.domain.model.user.password.ChangePasswordData
import com.bravedeveloper.domain.model.user.password.ChangePasswordResponse
import com.bravedeveloper.domain.model.user.password.last.LastPasswordData
import com.bravedeveloper.domain.model.user.password.last.LastPasswordResponse
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordData
import com.bravedeveloper.domain.model.user.rating.RateUserData
import com.bravedeveloper.domain.model.user.registration.SignUpData
import com.bravedeveloper.domain.model.user.registration.SignUpResponse
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileData
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileResponse
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeData
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface SandBaseService {

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun signIn(@Body body: String): Single<ResponseData<SignInData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun signUp(@Body body: String): Single<ResponseData<SignUpData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun updateProfile(@Body body: String): Single<ResponseData<UpdateProfileData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun changePassword(@Body body: String): Single<ResponseData<ChangePasswordData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun lastPasswordUpdate(@Body body: String): Single<ResponseData<LastPasswordData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getOrdersUnauthorized(@Body body: String): Single<ResponseData<OrdersUnauthorizedData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun deleteOwnAccount(@Body body: String): Single<ResponseData<DeleteOwnAccountData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getVerificationSmsCode(@Body body: String): Single<ResponseData<VerificationSmsCodeData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun resetPassword(@Body body: String): Single<ResponseData<ResetPasswordData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getMe(@Body body: String): Single<ResponseData<MeData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun removeAvatar(@Body body: String): Single<ResponseData<RemoveAvatarData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getNotifications(@Body body: String): Single<ResponseData<NotificationsData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun markAsRead(@Body body: String): Single<ResponseData<MarkAsReadData>>

    @Multipart
    @POST("graphql")
    fun uploadAvatar(@Part("operations") body: String, @Part("map") mapBody: String, @Part image: MultipartBody.Part): Single<ResponseData<UploadAvatarData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getOrders(@Body body: String): Single<ResponseData<OrdersData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getOrder(@Body body: String): Single<ResponseData<OrderData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun createReply(@Body body: String): Single<ResponseData<CreateReplyData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getCargo(@Body body: String): Single<ResponseData<CargoData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun orderCreate(@Body body: String): Single<ResponseData<OrderCreateData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun removeReply(@Body body: String): Single<ResponseData<RemoveReplyData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun removeOrders(@Body body: String): Single<ResponseData<RemoveOrdersData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun approveReply(@Body body: String): Single<ResponseData<ApproveReplyData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun upsertFCMToken(@Body body: String): Single<ResponseData<UpsertFCMTokenData>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun getCitiesBySubstring(@Body body: String): Single<ResponseData<CitiesPagination>>

    @Headers("content-Type: application/json")
    @POST("graphql")
    fun rateUser(@Body body: String): Single<ResponseData<RateUserData>>
}
