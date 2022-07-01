package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeData
import com.bravedeveloper.domain.model.user.verification.VerificationSmsCodeInput
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetVerificationSmsCodeUseCase(private val repository: UserRepository) :
    SingleUseCase<ResponseData<VerificationSmsCodeData>> {

    private var input : VerificationSmsCodeInput? = null

    fun saveInput(input: VerificationSmsCodeInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<VerificationSmsCodeData>>? {
        return input?.let { repository.getVerificationSmsCode(it) }
    }

}