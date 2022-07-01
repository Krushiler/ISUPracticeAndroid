package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.password.last.LastPasswordData
import com.bravedeveloper.domain.model.user.password.last.LastPasswordResponse
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class LastPasswordSingleUseCase(
    val repository: UserRepository
) : SingleUseCase<ResponseData<LastPasswordData>> {

    override fun execute(): Single<ResponseData<LastPasswordData>> {
        return repository.lastPasswordUpdate()
    }
}