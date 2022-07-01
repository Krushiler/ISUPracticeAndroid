package com.bravedeveloper.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.bravedeveloper.data.remote.api.util.Constants
import com.bravedeveloper.domain.repository.FirstTimeUseManager

class FirstTimeUseManagerImpl(private val appPreferences: SharedPreferences) : FirstTimeUseManager {
    override fun setFirstTimeUse(firstTime: Boolean) {
        appPreferences.edit {
            putBoolean(Constants.FIRST_TIME_APP_USE, true)
            apply()
        }
    }

    override fun getFirstTimeUse() =
        appPreferences.getBoolean(Constants.FIRST_TIME_APP_USE, true)

}