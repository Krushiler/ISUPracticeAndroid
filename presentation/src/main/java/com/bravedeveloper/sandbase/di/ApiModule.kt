package com.bravedeveloper.sandbase.di

import com.bravedeveloper.data.remote.api.SandBaseApi
import com.bravedeveloper.data.remote.api.SandBaseService
import com.bravedeveloper.data.remote.api.dadata.DadataApi
import com.bravedeveloper.data.remote.api.dadata.DadataService
import com.bravedeveloper.data.repository.AddressesRepositoryImpl
import com.bravedeveloper.data.repository.OrdersRepositoryImpl
import com.bravedeveloper.data.repository.UserRepositoryImpl
import com.bravedeveloper.data.repository.UserUnauthorizedRepositoryImpl
import com.bravedeveloper.domain.repository.AddressesRepository
import com.bravedeveloper.domain.repository.OrdersRepository
import com.bravedeveloper.domain.repository.UserRepository
import com.bravedeveloper.domain.repository.UserUnauthorizedRepository
import com.bravedeveloper.domain.usecase.*
import com.bravedeveloper.domain.usecase.addresses.GetCoordinatesByAddressSingleUseCase
import com.bravedeveloper.domain.usecase.addresses.GetAddressByCoordinatesSingleUseCase
import com.bravedeveloper.domain.usecase.addresses.GetSuggestedAddressesSingleUseCase
import com.bravedeveloper.domain.usecase.addresses.GetSuggestedCitySingleUseCase
import com.bravedeveloper.domain.usecase.notifications.GetNotificationsUseCase
import com.bravedeveloper.domain.usecase.notifications.MarkAsReadUseCase
import com.bravedeveloper.domain.usecase.notifications.fcm.UpsertFCMTokenUseCase
import com.bravedeveloper.domain.usecase.orders.GetCargoSingleUseCase
import com.bravedeveloper.domain.usecase.orders.GetOrderSingleUseCase
import com.bravedeveloper.domain.usecase.orders.GetOrdersSingleUseCase
import com.bravedeveloper.domain.usecase.orders.OrdersUnauthorizedSingleUseCase
import com.bravedeveloper.domain.usecase.orders.customer.ApproveReplySingleUseCase
import com.bravedeveloper.domain.usecase.orders.customer.OrderCreateSingleUseCase
import com.bravedeveloper.domain.usecase.orders.customer.RemoveOrdersSingleUseCase
import com.bravedeveloper.domain.usecase.orders.seller.CreateReplySingleUseCase
import com.bravedeveloper.domain.usecase.orders.seller.RemoveReplySingleUseCase
import com.bravedeveloper.sandbase.presentation.global.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideApi(): SandBaseService {
        return SandBaseApi.getService()
    }

    @Singleton
    @Provides
    fun provideDadataApi(): DadataService {
        return DadataApi.getService()
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        api: SandBaseService
    ): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideUserUnauthorizedRepository(
        api: SandBaseService
    ): UserUnauthorizedRepository {
        return UserUnauthorizedRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideOrdersRepository(
        api: SandBaseService
    ): OrdersRepository {
        return OrdersRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideAddressesRepository(
        api: DadataService,
        sandBaseService: SandBaseService
    ): AddressesRepository {
        return AddressesRepositoryImpl(api, sandBaseService)
    }


    @Singleton
    @Provides
    fun provideSignInUseCase(repository: UserRepository): SignInSingleUseCase {
        return SignInSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(repository: UserRepository): SignUpSingleUseCase {
        return SignUpSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileUseCase(repository: UserRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideChangePasswordUseCase(repository: UserRepository): ChangePasswordSingleUseCase {
        return ChangePasswordSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideLastPasswordUseCase(repository: UserRepository): LastPasswordSingleUseCase {
        return LastPasswordSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideOrdersUnauthorizedUseCase(repository: UserUnauthorizedRepository): OrdersUnauthorizedSingleUseCase {
        return OrdersUnauthorizedSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteOwnAccountUseCase(repository: UserRepository): DeleteOwnAccountUseCase {
        return DeleteOwnAccountUseCase(repository)
    }

    @Singleton
    @Provides
    fun provedGetVerificationSmsCodeUseCase(repository: UserRepository): GetVerificationSmsCodeUseCase {
        return GetVerificationSmsCodeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provedResetPasswordUseCase(repository: UserRepository): ResetPasswordUseCase {
        return ResetPasswordUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetMeUseCase(repository: UserRepository): GetMeUseCase {
        return GetMeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRemoveAvatarUseCase(repository: UserRepository): RemoveAvatarSingleUseCase {
        return RemoveAvatarSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUploadAvatarSingleUseCase(repository: UserRepository): UploadAvatarSingleUseCase {
        return UploadAvatarSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetOrdersSingleUseCase(repository: OrdersRepository): GetOrdersSingleUseCase {
        return GetOrdersSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetOrderSingleUseCase(repository: OrdersRepository): GetOrderSingleUseCase {
        return GetOrderSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideCreateReplySingleUseCase(repository: OrdersRepository): CreateReplySingleUseCase {
        return CreateReplySingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetCargoSingleUseCase(repository: OrdersRepository): GetCargoSingleUseCase {
        return GetCargoSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideOrderCreateSingleUseCase(repository: OrdersRepository): OrderCreateSingleUseCase {
        return OrderCreateSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRemoveReplySingleUseCase(repository: OrdersRepository): RemoveReplySingleUseCase {
        return RemoveReplySingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUser(): User {
        return User()
    }

    @Singleton
    @Provides
    fun provideGetSuggestedAddressesSingleUseCase(repository: AddressesRepository): GetSuggestedAddressesSingleUseCase {
        return GetSuggestedAddressesSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAddressByCoordinatesSingleUseCase(repository: AddressesRepository): GetAddressByCoordinatesSingleUseCase {
        return GetAddressByCoordinatesSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetCoordinatesByAddressSingleUseCase(repository: AddressesRepository): GetCoordinatesByAddressSingleUseCase {
        return GetCoordinatesByAddressSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetNotificationsSingleUseCase(repository: UserRepository): GetNotificationsUseCase {
        return GetNotificationsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideMarkAsReadUseCase(repository: UserRepository): MarkAsReadUseCase {
        return MarkAsReadUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRemoveOrdersUseCase(repository: OrdersRepository): RemoveOrdersSingleUseCase {
        return RemoveOrdersSingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideApproveReplyUseCase(repository: OrdersRepository): ApproveReplySingleUseCase {
        return ApproveReplySingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpsertFCMTokenUseCase(repository: UserRepository): UpsertFCMTokenUseCase {
        return UpsertFCMTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSuggestedCitySingleUseCase(repository: AddressesRepository): GetSuggestedCitySingleUseCase {
        return GetSuggestedCitySingleUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRateUserSingleUseCase(repository: UserRepository): RateUserSingleUseCase {
        return RateUserSingleUseCase(repository = repository)
    }
}