package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.registration.SignUpData
import com.bravedeveloper.domain.model.user.registration.SignUpInput
import com.bravedeveloper.domain.model.user.registration.SignUpResponse
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class SignUpSingleUseCase(
    val repository: UserRepository
) : SingleUseCase<ResponseData<SignUpData>> {

    private var input: SignUpInput? = null

    fun saveInput(input: SignUpInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<SignUpData>>? {
        return input?.let { repository.signUp(it) }
    }

}