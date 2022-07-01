package com.bravedeveloper.sandbase.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {
    @Singleton
    @Provides
    fun provideCicerone(): Cicerone<Router> {
        return Cicerone.create()
    }

    @Singleton
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Singleton
    @Provides
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}