package com.bravedeveloper.domain.usecase.notifications

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadData
import com.bravedeveloper.domain.model.user.notifications.markasread.MarkAsReadInput
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class MarkAsReadUseCase(private val userRepository: UserRepository) :
    SingleUseCase<ResponseData<MarkAsReadData>> {

    private var input: MarkAsReadInput? = null

    fun saveInput(input: MarkAsReadInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<MarkAsReadData>>? {
        return input?.let { userRepository.markAsRead(it) }
    }
}