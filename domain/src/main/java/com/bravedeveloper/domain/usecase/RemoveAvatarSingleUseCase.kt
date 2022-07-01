package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.avatar.removeAvatar.RemoveAvatarData
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class RemoveAvatarSingleUseCase(private val repository: UserRepository) :
    SingleUseCase<ResponseData<RemoveAvatarData>> {
    override fun execute(): Single<ResponseData<RemoveAvatarData>> {
        return repository.removeAvatar()
    }
}