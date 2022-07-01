package com.bravedeveloper.domain.usecase.addresses

import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressInput
import com.bravedeveloper.domain.model.addresses.suggestions.AddressByCoordinatesInput
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsData
import com.bravedeveloper.domain.repository.AddressesRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetCoordinatesByAddressSingleUseCase(private val addressesRepository: AddressesRepository) :
    SingleUseCase<List<CoordinatesByAddressData>> {

    private var input: CoordinatesByAddressInput? = null

    fun saveInput(input: CoordinatesByAddressInput){
        this.input = input
    }

    override fun execute(): Single<List<CoordinatesByAddressData>>? {
        return input?.let { addressesRepository.getCoordinatesByAddress(it) }
    }
}