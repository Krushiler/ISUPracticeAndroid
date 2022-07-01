package com.bravedeveloper.domain.usecase.notifications

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.user.notifications.NotificationsData
import com.bravedeveloper.domain.model.user.notifications.NotificationsInput
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetNotificationsUseCase(private val userRepository: UserRepository) : SingleUseCase<ResponseData<NotificationsData>> {

    private var input: NotificationsInput? = null

    fun saveInput(input: NotificationsInput){
        this.input = input
    }

    override fun execute(): Single<ResponseData<NotificationsData>>? {
        return input?.let { userRepository.getNotifications(it) }
    }
}