package com.bravedeveloper.domain.usecase

import android.graphics.Bitmap
import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.avatar.uploadavatar.UploadAvatarData
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class UploadAvatarSingleUseCase(private val repository: UserRepository) :
    SingleUseCase<ResponseData<UploadAvatarData>> {

    private var input : Bitmap? = null

    fun saveInput(input: Bitmap) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<UploadAvatarData>>? {
        return input?.let { repository.uploadAvatar(it) }
    }
}