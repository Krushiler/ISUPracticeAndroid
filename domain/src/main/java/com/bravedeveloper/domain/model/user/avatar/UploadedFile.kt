package com.bravedeveloper.domain.model.user.avatar

import com.bravedeveloper.domain.model.user.Me
import com.bravedeveloper.domain.model.user.usertypes.ClientsUnion

data class UploadedFile(
    val id: String?,
    val name: String?,
    val mimeType: String?,
    val type: UploadTypesEnum?,
    val formats: ImageFormats?,
    val createdAt: String?,
    val updatedAt: String?,
    val user: Me?
)