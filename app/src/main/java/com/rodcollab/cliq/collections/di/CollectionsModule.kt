package com.rodcollab.cliq.collections.di

import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCase
import com.rodcollab.cliq.collections.bookings.domain.GetBookingsUseCaseImpl
import com.rodcollab.cliq.collections.bookings.form.OnQueryTextChangeUseCase
import com.rodcollab.cliq.collections.bookings.form.OnQueryTextChangeUseCaseImpl
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCase
import com.rodcollab.cliq.collections.clients.domain.GetClientsUseCaseImpl
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
}