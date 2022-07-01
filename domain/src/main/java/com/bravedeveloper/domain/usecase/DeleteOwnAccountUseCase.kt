package com.bravedeveloper.domain.usecase

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountData
import com.bravedeveloper.domain.model.user.deleteownaccount.DeleteOwnAccountInput
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class DeleteOwnAccountUseCase(private val repository: UserRepository) :
    SingleUseCase<ResponseData<DeleteOwnAccountData>> {

    private var input : DeleteOwnAccountInput? = null

    fun saveInput(input : DeleteOwnAccountInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<DeleteOwnAccountData>>? {
        return input?.let { repository.deleteOwnAccount(it) }
    }

}