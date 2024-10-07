package com.thierrystpierre.rides.di

import com.thierrystpierre.rides.data.api.VehicleApi
import com.thierrystpierre.rides.data.api.VehicleApiManager
import com.thierrystpierre.rides.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiComponent {
    @Singleton
    @Provides
    fun proviceApiManager(vehicleApi : VehicleApi) : VehicleApiManager = VehicleApiManager(
        vehicleApi = vehicleApi)

    @Provides
    fun getHttpClient() = KtorClient.initHttp()

}