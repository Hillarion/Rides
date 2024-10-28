package com.thierrystpierre.rides.di

import com.thierrystpierre.rides.util.ErrorBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SystemComponent {
    @Singleton
    @Provides
    fun proviceErrorBus() : ErrorBus = ErrorBus()

}