package com.rodcollab.cliq.di

import android.app.Application
import com.rodcollab.cliq.core.database.AppDatabase
import com.rodcollab.cliq.core.database.BookingDao
import com.rodcollab.cliq.core.database.ClientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application) : AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun providesClientDao(database: AppDatabase) : ClientDao {
        return database.clientDao()
    }

    @Singleton
    @Provides
    fun providesBookingDao(database: AppDatabase) : BookingDao {
        return database.bookingDao()
    }

}