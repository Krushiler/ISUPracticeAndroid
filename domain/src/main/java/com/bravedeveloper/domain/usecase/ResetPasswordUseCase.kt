package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordData
import com.bravedeveloper.domain.model.user.password.reset.ResetPasswordInput
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class ResetPasswordUseCase(
    val repository: UserRepository
) : SingleUseCase<ResponseData<ResetPasswordData>>{

    private var input: ResetPasswordInput? = null

    fun saveInput(input: ResetPasswordInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<ResetPasswordData>>? {
        return input?.let { repository.resetPassword(it) }
    }

}