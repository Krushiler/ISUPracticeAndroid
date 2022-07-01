package com.bravedeveloper.sandbase.presentation.settings.settingsnotifications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedeveloper.domain.model.ResponseData
import com.bravedeveloper.domain.model.city.CitiesPagination
import com.bravedeveloper.domain.model.city.City
import com.bravedeveloper.domain.model.city.CityName
import com.bravedeveloper.domain.model.city.CitySuggestionsData
import com.bravedeveloper.domain.usecase.addresses.GetSuggestedCitySingleUseCase
import com.bravedeveloper.sandbase.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SettingsNotificationViewModel @Inject constructor(
    private val getSuggestedCitySingleUseCase: GetSuggestedCitySingleUseCase
) : BaseViewModel() {

    private val suggestedCityLiveData = MutableLiveData<List<CityName>>()

    fun getSuggestedCityLiveData() = suggestedCityLiveData

    fun updateFilter(input: CitySuggestionsData) {
        getSuggestedCitySingleUseCase.saveInput(input = input)
    }

    fun getCitiesBySubstring() {
        getSuggestedCitySingleUseCase.execute()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                suggestedCityLiveData.value = it.data?.cities?.items
            }, {
                Log.e(it::class.simpleName, it.message.toString())
            })?.untilCleared()
    }
}