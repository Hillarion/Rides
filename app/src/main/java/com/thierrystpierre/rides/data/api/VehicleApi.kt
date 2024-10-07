package com.thierrystpierre.rides.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
//import retrofit2.http.GET
//import retrofit2.http.Query
import javax.inject.Inject


class VehicleApi @Inject constructor(private val ktorClient : HttpClient) {

    suspend fun getVehicles(numberItems : Int) : HttpResponse = ktorClient.get("https://random-data-api.com/api/vehicle/random_vehicle") {
        parameter("size", numberItems)
        parameter("is_xml", false)
    }
}

/*interface VehicleApi {

    @GET("vehicle/random_vehicle")
    suspend fun getVehicles(@Query("uid")size : Int) : HttpResponse
}*/
