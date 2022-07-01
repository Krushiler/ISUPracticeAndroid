package com.bravedeveloper.domain.usecase.addresses

import com.bravedeveloper.domain.model.addresses.suggestions.AddressByCoordinatesInput
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsData
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsInput
import com.bravedeveloper.domain.repository.AddressesRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetAddressByCoordinatesSingleUseCase(private val addressesRepository: AddressesRepository) :
    SingleUseCase<SuggestionsData> {

    private var input: AddressByCoordinatesInput? = null

    fun saveInput(input: AddressByCoordinatesInput){
        this.input = input
    }

    override fun execute(): Single<SuggestionsData>? {
        return input?.let { addressesRepository.getAddressByCoordinates(it) }
    }
}