package com.bravedeveloper.domain.usecase.orders

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.cargo.CargoData
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetCargoSingleUseCase(private val repository: OrdersRepository) : SingleUseCase<ResponseData<CargoData>> {
    override fun execute(): Single<ResponseData<CargoData>> {
        return repository.getCargo()
    }
}