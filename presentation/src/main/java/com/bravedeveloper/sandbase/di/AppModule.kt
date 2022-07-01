package com.bravedeveloper.sandbase.di

import android.content.Context
import android.content.SharedPreferences
import com.bravedeveloper.data.local.FirstTimeUseManagerImpl
import com.bravedeveloper.data.local.TokenManagerImpl
import com.bravedeveloper.data.remote.api.util.Constants
import com.bravedeveloper.domain.repository.FirstTimeUseManager
import com.bravedeveloper.domain.repository.TokenManager
import com.bravedeveloper.domain.usecase.local.GetFirstTimeUseUseCase
import com.bravedeveloper.domain.usecase.local.SetFirstTimeUseUseCase
import com.bravedeveloper.domain.usecase.token.DeleteTokenUseCase
import com.bravedeveloper.domain.usecase.token.LoadTokenUseCase
import com.bravedeveloper.domain.usecase.token.SaveTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun getResources(@ApplicationContext context: Context) = context.resources

    @Singleton
    @Provides
    fun getAppPreferences(@ApplicationContext context: Context) = context.getSharedPreferences(
        Constants.USER_PREF, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideTokenManager(appPreferences: SharedPreferences) : TokenManager {
        return TokenManagerImpl(appPreferences)
    }

    @Singleton
    @Provides
    fun provideFirstTimeUseManager(appPreferences: SharedPreferences): FirstTimeUseManager = FirstTimeUseManagerImpl(appPreferences)

    @Singleton
    @Provides
    fun provideSaveTokenUseCase(tokenManager: TokenManager) = SaveTokenUseCase(tokenManager)

    @Singleton
    @Provides
    fun provideDeleteTokenUseCase(tokenManager: TokenManager) = DeleteTokenUseCase(tokenManager)

    @Singleton
    @Provides
    fun provideLoadTokenUseCase(tokenManager: TokenManager) = LoadTokenUseCase(tokenManager)

    @Singleton
    @Provides
    fun provideSetFirstTimeUseUseCase(firstTimeUseManager: FirstTimeUseManager) = SetFirstTimeUseUseCase(firstTimeUseManager)

    @Singleton
    @Provides
    fun provideGetFirstTimeUseUseCase(firstTimeUseManager: FirstTimeUseManager) = GetFirstTimeUseUseCase(firstTimeUseManager)
}