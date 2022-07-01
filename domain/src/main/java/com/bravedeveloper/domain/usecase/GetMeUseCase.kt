package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.MeData
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetMeUseCase(private val repository: UserRepository) : SingleUseCase<ResponseData<MeData>> {
    override fun execute(): Single<ResponseData<MeData>> {
        return repository.getMe()
    }
}