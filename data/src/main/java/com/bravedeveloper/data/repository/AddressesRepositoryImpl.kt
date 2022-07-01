package com.bravedeveloper.data.repository

import com.bravedeveloper.data.remote.api.SandBaseService
import com.bravedeveloper.data.remote.api.dadata.DadataService
import com.bravedeveloper.data.remote.api.request.Request
import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressInput
import com.bravedeveloper.domain.model.addresses.suggestions.AddressByCoordinatesInput
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsData
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsInput
import com.bravedeveloper.domain.model.city.CitiesPagination
import com.bravedeveloper.domain.model.city.CitySuggestionsData
import com.bravedeveloper.domain.repository.AddressesRepository
import io.reactivex.Single
import org.json.JSONObject

class AddressesRepositoryImpl(
    private val api: DadataService,
    private val sandBaseService: SandBaseService
) : AddressesRepository {
    override fun getSuggestedAddresses(input: SuggestionsInput): Single<SuggestionsData> {
        return api.getSuggestedAddresses(input.query)
    }

    override fun getAddressByCoordinates(input: AddressByCoordinatesInput): Single<SuggestionsData> {
        return api.getAddressByCoordinates(input.lat, input.long)
    }

    override fun getCoordinatesByAddress(input: CoordinatesByAddressInput): Single<List<CoordinatesByAddressData>> {
        return api.getCoordinatesByAddress("[ \"" + input.query + "\" ]")
    }

    override fun getSuggestedCity(input: CitySuggestionsData): Single<ResponseData<CitiesPagination>> {
        val paramObject = JSONObject()
        paramObject.put("query", Request.getCitiesBySubstring(input = input))
        return sandBaseService.getCitiesBySubstring(paramObject.toString())
    }
}