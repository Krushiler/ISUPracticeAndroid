package com.bravedeveloper.domain.model.user.usertypes

import com.bravedeveloper.domain.model.city.City
import com.bravedeveloper.domain.model.user.usertypes.other.FcmToken
import com.bravedeveloper.domain.model.user.usertypes.other.Rating
import com.bravedeveloper.domain.model.user.avatar.UploadedFile

interface UserInterface {
    val id: String?
    val name: String?
    val phone: String?
    val email: String?
    val emailVerified: String?
    val rating: Int?
    val role: UserRoleEnum?
    val outgoingRates: Array<Rating>?
    val incomingRates: Array<Rating>?
    val appointmentsCount: Int?
    val rejectionsCount: Int?
    val city: City?
    val fcmTokens: Array<FcmToken>?
    val avatar: UploadedFile?
    val createdAt: String?
    val updatedAt: String?
    val lastActivity: String?
    val verified: Boolean?
}