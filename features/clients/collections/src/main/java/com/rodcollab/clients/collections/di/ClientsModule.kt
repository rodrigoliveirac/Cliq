package com.rodcollab.clients.collections.di

import com.rodcollab.clients.collections.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class ClientsModule {

    @Singleton
    @Binds
    abstract fun providesGetClientsUseCase(impl: GetClientsUseCaseImpl): GetClientsUseCase

}