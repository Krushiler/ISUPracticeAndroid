package com.bravedeveloper.domain.usecase.addresses

import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsData
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsInput
import com.bravedeveloper.domain.repository.AddressesRepository
import com.bravedeveloper.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class GetSuggestedAddressesSingleUseCase(private val addressesRepository: AddressesRepository) : SingleUseCase<SuggestionsData> {

    private var input: SuggestionsInput? = null

    fun saveInput(input: SuggestionsInput){
        this.input = input
    }

    override fun execute(): Single<SuggestionsData>? {
        return input?.let { addressesRepository.getSuggestedAddresses(it) }
    }

}