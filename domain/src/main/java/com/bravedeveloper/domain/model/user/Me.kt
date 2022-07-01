package com.bravedeveloper.domain.model.user

import com.bravedeveloper.domain.model.city.City
import com.bravedeveloper.domain.model.user.notifications.UnreadNotificationsType
import com.bravedeveloper.domain.model.user.avatar.UploadedFile
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum
import com.squareup.moshi.Json

data class Me(
    @Json(name="userId") var userId : String?,
    @Json(name="name") var name : String?,
    @Json(name="phone") var phone : String?,
    @Json(name="role") var role : UserRoleEnum?,
    @Json(name="email") var email : String?,
    @Json(name="emailVerified") var emailVerified : Boolean?,
    @Json(name="city") var city : City?,
    @Json(name="avatar") var avatar : UploadedFile?,
    @Json(name="replyPerDayLimit") var replyPerDayLimit : Int?,
    @Json(name="lastDayReplyCount") var lastDayReplyCount : Int?,
    @Json(name="unreadNotificationsCount") var unreadNotificationsCount : Int?,
    @Json(name="unreadNotifications") var unreadNotifications : UnreadNotificationsType?,
    @Json(name="createdAt") var createdAt : String?
)