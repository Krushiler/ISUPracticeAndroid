package com.bravedeveloper.domain.repository

import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressInput
import com.bravedeveloper.domain.model.addresses.suggestions.AddressByCoordinatesInput
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsData
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsInput
import com.bravedeveloper.domain.model.city.CitiesPagination
import com.bravedeveloper.domain.model.city.CitySuggestionsData
import io.reactivex.Single

interface AddressesRepository {

    fun getSuggestedAddresses(input: SuggestionsInput): Single<SuggestionsData>

    fun getAddressByCoordinates(input: AddressByCoordinatesInput): Single<SuggestionsData>

    fun getCoordinatesByAddress(input: CoordinatesByAddressInput): Single<List<CoordinatesByAddressData>>

    fun getSuggestedCity(input: CitySuggestionsData): Single<ResponseData<CitiesPagination>>
}