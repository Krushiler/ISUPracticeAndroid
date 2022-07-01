package com.bravedeveloper.domain.usecase.notifications.fcm

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenData
import com.bravedeveloper.domain.model.user.notifications.fcm.UpsertFCMTokenInput
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import java.lang.StringBuilder

inline fun buildStr(a: Int, building: StringBuilder.() -> Unit): String {
    val builder = StringBuilder()
    building.invoke(builder)
    return builder.toString()
}

class UpsertFCMTokenUseCase(private val userRepository: UserRepository) :
    SingleUseCase<ResponseData<UpsertFCMTokenData>> {
    private var input: UpsertFCMTokenInput? = null

    fun saveInput(input: UpsertFCMTokenInput) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<UpsertFCMTokenData>>? {
        return input?.let { userRepository.upsertFCMTokenUseCase(it) }
    }
}