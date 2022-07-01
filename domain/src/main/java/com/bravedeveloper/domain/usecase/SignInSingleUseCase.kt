package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.authorization.SignIn
import com.bravedeveloper.domain.model.user.authorization.SignInData
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import com.bravedeveloper.domain.model.user.authorization.SignInInput
import com.bravedeveloper.domain.model.user.authorization.SignInResponse
import com.bravedeveloper.domain.repository.UserRepository
import io.reactivex.Single

class SignInSingleUseCase(
    val repository: UserRepository
) : SingleUseCase<ResponseData<SignInData>> {

    private var input: SignInInput? = null

    fun saveInput(input: SignInInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<SignInData>>? {
        return input?.let { repository.signIn(it) }
    }

}