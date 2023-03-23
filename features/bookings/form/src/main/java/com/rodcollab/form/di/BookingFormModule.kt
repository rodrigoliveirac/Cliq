package com.rodcollab.form.di

import com.rodcollab.form.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class BookingFormModule {

    @Singleton
    @Binds
    abstract fun providesGetClientsUseCase(impl: GetClientsUseCaseImpl): GetClientsUseCase

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