package com.bravedeveloper.domain.model.user.usertypes

import com.bravedeveloper.domain.model.city.City
import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.user.avatar.UploadedFile
import com.bravedeveloper.domain.model.user.usertypes.other.Company
import com.bravedeveloper.domain.model.user.usertypes.other.Entrepreneur
import com.bravedeveloper.domain.model.user.usertypes.other.FcmToken
import com.bravedeveloper.domain.model.user.usertypes.other.Rating

data class DispatcherType(
    override val id: String?,
    override val name: String?,
    override val phone: String?,
    override val email: String?,
    override val emailVerified: String?,
    override val rating: Int?,
    override val role: UserRoleEnum?,
    override val outgoingRates: Array<Rating>?,
    override val incomingRates: Array<Rating>?,
    override val appointmentsCount: Int?,
    override val rejectionsCount: Int?,
    override val city: City?,
    override val fcmTokens: Array<FcmToken>?,
    override val avatar: UploadedFile?,
    override val createdAt: String?,
    override val updatedAt: String?,
    override val lastActivity: String?,
    override val verified: Boolean?,
    val sellerProperties: SellerProperties?,
    val entrepreneur: Entrepreneur?,
    val approvedOrders: Array<Order>?,
    val sellerIn: Company
) : UserInterface
