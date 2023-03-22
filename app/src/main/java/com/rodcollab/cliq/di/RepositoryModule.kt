package com.rodcollab.cliq.di

import com.rodcollab.cliq.core.repository.BookingRepository
import com.rodcollab.cliq.core.repository.BookingRepositoryImpl
import com.rodcollab.cliq.core.repository.ClientRepository
import com.rodcollab.cliq.core.repository.ClientRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesClientRepository(impl: ClientRepositoryImpl) : ClientRepository

    @Singleton
    @Binds
    abstract fun providesBookingRepository(impl: BookingRepositoryImpl) : BookingRepository
}