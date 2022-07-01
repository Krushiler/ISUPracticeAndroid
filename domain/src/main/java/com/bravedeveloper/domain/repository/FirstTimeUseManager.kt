package com.bravedeveloper.domain.repository

interface FirstTimeUseManager {

    fun setFirstTimeUse(firstTime: Boolean)

    fun getFirstTimeUse(): Boolean

}