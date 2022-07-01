package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileData
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileInput
import com.bravedeveloper.domain.model.user.updateprofile.UpdateProfileResponse
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class UpdateProfileUseCase(
    val repository: UserRepository
) : SingleUseCase<ResponseData<UpdateProfileData>> {

    private var input: UpdateProfileInput? = null

    fun saveInput(input: UpdateProfileInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<UpdateProfileData>>? {
        return input?.let { repository.updateProfile(it) }
    }
}