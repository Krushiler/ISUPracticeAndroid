package com.bravedeveloper.sandbase.di

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class MapModule {
    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder = Geocoder(context)
}