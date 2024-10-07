package com.thierrystpierre.rides.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thierrystpierre.rides.data.api.VehicleApiManager
import com.thierrystpierre.rides.data.models.SortedVehicle
import com.thierrystpierre.rides.data.models.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val apiManager : VehicleApiManager
): ViewModel() {

    var quantity : Int? = null
    val sortValue : Boolean = false;
    var sortedVehicles = MutableLiveData<List<SortedVehicle>>()
    var vehicles = mutableListOf<Vehicle>()


    fun getVehicle(vin : String) : Vehicle {
        Log.d("ListViewModel", "getVehicle($vin) ")
        return vehicles.filter { ve -> ve.vin == vin}.first()
    }

    fun sortVehicle(vehicles : List<Vehicle>, sortValue : Boolean) : List<SortedVehicle> {
        Log.d("ListViewModel", "sortVehicle(vehicles, $sortValue)")
        val result =  vehicles.toMutableList()
            .map { ve -> SortedVehicle(ve.vin, ve.make_and_model) }
            .toMutableList()
            .sortedWith(if (!sortValue) compareBy { it.vin } else compareBy { it.make_and_model })
        return result
    }

    fun fetchVehicles() {
        Log.d("ListViewModel", "fetchVehicles($quantity) ")
        viewModelScope.launch {
            apiManager.getVehicles(quantity ?: 1,
                onResult = { response ->
                    Log.d("ListViewModel", "fetchVehicles() response = ${response.data} ")
                    response.data?.let{
                        vehicles =  it.toMutableList()
                        sortedVehicles.postValue(sortVehicle(vehicles, sortValue))
                    }
                },
                onError = { response ->
                    Log.d("ListViewModel", "getVehicles() error = $response ")

                }
            )
        }
    }

}