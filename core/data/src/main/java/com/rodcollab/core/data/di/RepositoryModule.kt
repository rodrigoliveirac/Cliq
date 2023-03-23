package com.rodcollab.core.data.di

import com.rodcollab.core.data.repository.BookingRepository
import com.rodcollab.core.data.repository.BookingRepositoryImpl
import com.rodcollab.core.data.repository.ClientRepository
import com.rodcollab.core.data.repository.ClientRepositoryImpl
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