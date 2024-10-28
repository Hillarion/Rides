package com.thierrystpierre.rides.viewmodels

import androidx.lifecycle.ViewModel
import com.thierrystpierre.rides.data.models.Vehicle

class VehicleViewModel : ViewModel()  {
    var vehicle : Vehicle? = null
    private val simpleRate = 1.0
    private val higherRate = 1.5
    private val changeRateThreshold = 5000

    fun getCarbonEmission() : Double {
        var carbonEmission = 0.0
        vehicle?.let {
            val kilometers = it.kilometrage
            if (kilometers <= changeRateThreshold) {
                carbonEmission = simpleRate(kilometers)
            } else {
                carbonEmission = simpleRate(changeRateThreshold) +
                        highRate(kilometers - changeRateThreshold)
            }
        }
        return carbonEmission
    }
    fun simpleRate(km : Int) : Double {
        return km * simpleRate
    }
    fun highRate(km : Int) : Double {
        return km * higherRate
    }
}