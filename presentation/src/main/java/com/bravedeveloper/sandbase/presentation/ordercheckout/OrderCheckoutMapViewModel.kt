package com.bravedeveloper.sandbase.presentation.ordercheckout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressData
import com.bravedeveloper.domain.model.addresses.coordinatesbyaddress.CoordinatesByAddressInput
import com.bravedeveloper.domain.model.addresses.suggestions.AddressByCoordinatesInput
import com.bravedeveloper.domain.model.addresses.suggestions.Suggestion
import com.bravedeveloper.domain.model.addresses.suggestions.SuggestionsInput
import com.bravedeveloper.domain.model.city.hours.ReceivingHoursEnum
import com.bravedeveloper.domain.model.city.order.ordercreate.OrderCreateInput
import com.bravedeveloper.domain.usecase.addresses.GetAddressByCoordinatesSingleUseCase
import com.bravedeveloper.domain.usecase.addresses.GetCoordinatesByAddressSingleUseCase
import com.bravedeveloper.domain.usecase.addresses.GetSuggestedAddressesSingleUseCase
import com.bravedeveloper.domain.usecase.orders.customer.OrderCreateSingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.bravedeveloper.sandbase.presentation.global.User
import com.bravedeveloper.sandbase.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OrderCheckoutMapViewModel @Inject constructor(
    private val getSuggestedAddressesSingleUseCase: GetSuggestedAddressesSingleUseCase,
    private val getAddressByCoordinatesSingleUseCase: GetAddressByCoordinatesSingleUseCase,
    private val getCoordinatesByAddressSingleUseCase: GetCoordinatesByAddressSingleUseCase,
    private val orderCreateSingleUseCase: OrderCreateSingleUseCase,
    private val user: User,
    private val router: Router
) : BaseViewModel() {

    private val suggestionsLiveData = MutableLiveData<List<Suggestion>>()
    private val addressByCoordinatesLiveData = MutableLiveData<Suggestion>()
    private val coordinatesByAddressLiveData = MutableLiveData<CoordinatesByAddressData>()
    private val createdOrderNumberLiveData = MutableLiveData<Int>()

    private val suggestingDisposable = CompositeDisposable()
    private val addressByCoordinatesDisposable = CompositeDisposable()
    private val coordinatesByAddressDisposable = CompositeDisposable()

    fun getSuggestion(query: String) {
        suggestingDisposable.clear()
        getSuggestedAddressesSingleUseCase.saveInput(SuggestionsInput(query))
        getSuggestedAddressesSingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                suggestionsLiveData.value = it.suggestions
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.let {
                suggestingDisposable.add(
                    it
                )
            }
    }

    fun getAddressByCoordinates(lat: Double, long: Double) {
        addressByCoordinatesDisposable.clear()
        getAddressByCoordinatesSingleUseCase.saveInput(AddressByCoordinatesInput(lat, long))
        getAddressByCoordinatesSingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (it.suggestions.isNotEmpty())
                    addressByCoordinatesLiveData.value = it.suggestions[0]
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.let {
                addressByCoordinatesDisposable.add(
                    it
                )
            }
    }

    fun getCoordinatesByAddress(address: String) {
        coordinatesByAddressDisposable.clear()
        getCoordinatesByAddressSingleUseCase.saveInput(CoordinatesByAddressInput(address))
        getCoordinatesByAddressSingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (it.isNotEmpty())
                    coordinatesByAddressLiveData.value = it[0]
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.let {
                coordinatesByAddressDisposable.add(
                    it
                )
            }
    }

    fun orderCreate(
        coords: Pair<Double, Double>,
        destination: String,
        cargoType: String,
        cargoSubType: String,
        cargoVolume: String,
        date: String,
        time: ReceivingHoursEnum,
        comment: String,
        measure: String
    ) {
        orderCreateSingleUseCase.saveInput(
            OrderCreateInput(
                coords,
                user.user?.name.toString(),
                user.user?.phone.toString(),
                destination,
                cargoType,
                cargoSubType,
                cargoVolume,
                date,
                time,
                comment,
                measure
            )
        ) {
            orderCreateSingleUseCase.execute()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    createdOrderNumberLiveData.value = it.data?.orderCreate?.record?.number
                }, {
                    Log.e(it::class.simpleName, it.message.toString())
                })?.untilCleared()
        }

    }

    fun getSuggestionsLiveData(): LiveData<List<Suggestion>> = suggestionsLiveData

    fun getAddressByCoordinatesLiveData(): LiveData<Suggestion> = addressByCoordinatesLiveData

    fun getCoordinatesByAddressLiveData(): LiveData<CoordinatesByAddressData> =
        coordinatesByAddressLiveData

    fun getCreatedOrderNumberLiveData(): LiveData<Int> = createdOrderNumberLiveData

    fun goToOrderInfoScreen() {
        router.navigateTo(Screens.orderInfoScreen())
        createdOrderNumberLiveData.value = null
    }

}