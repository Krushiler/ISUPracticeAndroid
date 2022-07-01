package com.bravedeveloper.domain.usecase.addresses

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.CitiesPagination
import com.bravedeveloper.domain.model.city.CitySuggestionsData
import com.bravedeveloper.domain.repository.AddressesRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetSuggestedCitySingleUseCase(
    private val addressesRepository: AddressesRepository
): SingleUseCase<ResponseData<CitiesPagination>> {

    private var input: CitySuggestionsData? = null

    fun saveInput(input: CitySuggestionsData) {
        this.input = input
    }

    override fun execute(): Single<ResponseData<CitiesPagination>>? {
        return input?.let { addressesRepository.getSuggestedCity(it) }
    }
}