package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.password.ChangePasswordData
import com.bravedeveloper.domain.model.user.password.ChangePasswordInput
import com.bravedeveloper.domain.model.user.password.ChangePasswordResponse
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class ChangePasswordSingleUseCase(
    val repository: UserRepository
) : SingleUseCase<ResponseData<ChangePasswordData>> {

    private var input: ChangePasswordInput? = null

    fun saveInput(input: ChangePasswordInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<ChangePasswordData>>? {
        return input?.let { repository.changePassword(it) }
    }
}