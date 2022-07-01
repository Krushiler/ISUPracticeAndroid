package com.bravedeveloper.sandbase.presentation.sellerfilter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedeveloper.domain.model.city.cargo.Cargo
import com.bravedeveloper.domain.model.city.cargo.CargoDataData
import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.usecase.orders.GetCargoSingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SellerFilterViewModel @Inject constructor(
    private val router: Router,
    private val getCargoSingleUseCase: GetCargoSingleUseCase
) : BaseViewModel() {

    private val filterLiveData = MutableLiveData(UserFilter(null, null, null, null, null, null, null))
    private val cargoTypes = MutableLiveData<CargoDataData>()

    fun getCargoTypesLiveData(): LiveData<CargoDataData> = cargoTypes

    fun applyFilter(
        materials: List<String>?,
        sort: OrdersSortEnum?,
        minimalValue: Int?,
        maximalValue: Int?
    ) {
        filterLiveData.value = UserFilter(
            null, sort, minimalValue, maximalValue, materials, null, null
        )
    }

    fun getCargoTypes() {
        getCargoSingleUseCase.execute()
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if (it != null) {
                    cargoTypes.value = it.data?.cargo

                }
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }

    fun navigateBack() {
        router.exit()
    }

    fun getFilterLiveData(): LiveData<UserFilter> = filterLiveData
}