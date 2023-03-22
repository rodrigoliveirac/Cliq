package com.rodcollab.cliq.collections.di

import com.rodcollab.cliq.collections.bookings.domain.*
import com.rodcollab.cliq.collections.bookings.form.*
import com.rodcollab.cliq.collections.clients.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CollectionsModule {

    @Singleton
    @Binds
    abstract fun providesGetClientsUseCase(impl: GetClientsUseCaseImpl): GetClientsUseCase

    @Singleton
    @Binds
    abstract fun providesGetBookingsUseCase(impl: GetBookingsUseCaseImpl): GetBookingsUseCase

    @Singleton
    @Binds
    abstract fun providesOnQueryTextChangeUseCase(impl: OnQueryTextChangeUseCaseImpl): OnQueryTextChangeUseCase

    @Singleton
    @Binds
    abstract fun providesOnSelectClientUseCase(impl: OnSelectedClientUseCaseImpl): OnSelectedClientUseCase

    @Singleton
    @Binds
    abstract fun providesGetLastClientNameUseCase(impl: GetLastClientNameUseCaseImpl): GetLastClientNameUseCase
}