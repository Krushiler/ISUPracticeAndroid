package com.bravedeveloper.domain.usecase.base

import io.reactivex.Single

interface SingleUseCase<T> {

    fun execute(): Single<T>?

}