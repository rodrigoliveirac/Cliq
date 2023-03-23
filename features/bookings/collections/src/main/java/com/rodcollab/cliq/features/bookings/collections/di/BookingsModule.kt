package com.rodcollab.cliq.features.bookings.collections.di

import com.rodcollab.cliq.features.bookings.collections.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class BookingsModule {

    @Singleton
    @Binds
    abstract fun providesGetBookingsUseCase(impl: GetBookingsUseCaseImpl): GetBookingsUseCase

}