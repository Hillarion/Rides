package com.thierrystpierre.rides.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thierrystpierre.rides.data.api.VehicleApiManager
import com.thierrystpierre.rides.data.models.SortedVehicle
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.util.ErrorBus
import com.thierrystpierre.rides.util.ErrorStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val apiManager : VehicleApiManager,
    private val errorBus : ErrorBus
): ViewModel() {

    var quantity : UInt? = null
    var sortValue : Boolean = false
        set(value) {
            field = value
            sortedVehicles.postValue(sortVehicle(vehicles, sortValue))
        }
    var sortedVehicles = MutableLiveData<List<SortedVehicle>>()
    var vehicles = mutableListOf<Vehicle>()

    fun getVehicle(vin : String) : Vehicle {
        return vehicles.filter { ve -> ve.vin == vin}.first()
    }

    fun sortVehicle(vehicles : List<Vehicle>, sortValue : Boolean) : List<SortedVehicle> {
        val result =  vehicles.toMutableList()
            .map { ve -> SortedVehicle(ve.vin, ve.make_and_model) }
            .toMutableList()
            .sortedWith(if (!sortValue) compareBy { it.vin } else compareBy { it.make_and_model })
        return result
    }

    fun fetchVehicles() {

        if(isValid(quantity)) {
            viewModelScope.launch {
                apiManager.getVehicles(quantity ?: 1.toUInt(),
                    onResult = { response ->
                        response.data?.let {
                            vehicles = it.toMutableList()
                            sortedVehicles.postValue(sortVehicle(vehicles, sortValue))
                        }
                    },
                    onError = { response ->
                        Log.d("ListViewModel", "getVehicles() error = $response ")
                    }
                )
            }
        } else {
            viewModelScope.launch {
                errorBus.publish(ErrorStatus("Error", "Acceptable values are\n1 to 100"))
            }
        }
    }

    fun isValid(sample : UInt?) : Boolean {
        sample?.let{
            return if ((it== 0.toUInt()) || (it > 100.toUInt()))
                false
            else
                true
        }
        return false
    }
}