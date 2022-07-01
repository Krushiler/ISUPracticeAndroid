package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.rating.RateInput
import com.bravedeveloper.domain.model.user.rating.RateUserData
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class RateUserSingleUseCase(
    private val repository: UserRepository
): SingleUseCase<ResponseData<RateUserData>> {

    private var input: RateInput? = null

    fun saveInput(input: RateInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<RateUserData>>? {
        return input?.let { repository.rateUser(it) }
    }
}