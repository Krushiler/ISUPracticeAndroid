package com.bravedeveloper.domain.model.user.usertypes.other

import com.bravedeveloper.domain.model.user.usertypes.ClientsUnion

data class Entrepreneur(
    val id: Int?,
    val TIN: Int?,
    val name: String?,
    val IEC: Int?,
    val VATType: VATTypesEnum?,
    val address: String?,
    val BIC: Int?,
    val bankName: String?,
    val correspondingAcc: Int?,
    val checkingAcc: Int?,
    val user: ClientsUnion?,
    val active: Boolean?,
    val createdAt: String?,
    val updatedAt: String?
)