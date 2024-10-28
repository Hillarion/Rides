package com.thierrystpierre.rides.data.api

import android.util.Log
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.network.ResponseErrorModel
import com.thierrystpierre.rides.network.ResponseModel
import io.ktor.client.call.body

class VehicleApiManager (
    private val vehicleApi : VehicleApi
) {
    internal suspend fun getVehicles(
        numberItems : UInt,
        onResult: suspend (ResponseModel<List<Vehicle>>) -> Unit,
        onError: suspend (ResponseErrorModel) -> Unit) {
        Log.d("VehicleApiManager", "getVehicles($numberItems) ")

        try {
            val response = vehicleApi.getVehicles(numberItems)
            if(response.status.value in 200 .. 299) {
                onResult(ResponseModel(
                    status = response.status.description,
                    code = response.status.value,
                    data = response.body()
                ))
            } else {
                onError(response.body())
            }
        } catch (he : Exception) {
            Log.e("VehicleApiManager", he.message ?: "vehicle fetch issue")
//            onError(ResponseErrorModel(error = ResponseError(he.message ?: "vehicle fetch issue", he.stackTraceToString())))
        }
    }
}