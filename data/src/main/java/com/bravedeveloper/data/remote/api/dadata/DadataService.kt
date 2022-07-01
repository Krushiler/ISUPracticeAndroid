package com.bravedeveloper.data.remote.api.dadata

import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressData
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsData
import io.reactivex.Single
import retrofit2.http.*

interface DadataService {

    @GET("/suggestions/api/4_1/rs/suggest/address")
    fun getSuggestedAddresses(@Query("query") suggestionQuery: String): Single<SuggestionsData>

    @GET("/suggestions/api/4_1/rs/geolocate/address")
    fun getAddressByCoordinates(@Query("lat") lat: Double, @Query("lon") long: Double): Single<SuggestionsData>

    @POST("https://cleaner.dadata.ru/api/v1/clean/address")
    @Headers("X-Secret: d871fc3693aba51c288c98b2e9b5e7a19f9bdbe5", "Content-Type: application/json")
    fun getCoordinatesByAddress(@Body address: String): Single<List<CoordinatesByAddressData>>
}