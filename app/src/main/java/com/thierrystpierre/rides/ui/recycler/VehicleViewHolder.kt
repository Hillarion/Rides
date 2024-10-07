package com.thierrystpierre.rides.ui.recycler

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.thierrystpierre.rides.data.models.SortedVehicle
import com.thierrystpierre.rides.databinding.VehicleRowBinding

interface RowClickedListener {
    fun onClicked(vin : String)
}
class VehicleViewHolder(private val itemBinding : VehicleRowBinding, private val listener : RowClickedListener)
    : RecyclerView.ViewHolder(itemBinding.root) {
    private lateinit var mVehicle : SortedVehicle

    fun configure(vehicle : SortedVehicle) {
        mVehicle = vehicle
        Log.d("VehicleViewHolder", "configure($mVehicle)")

        itemBinding.vin.text = mVehicle.vin
        itemBinding.make.text = mVehicle.make_and_model
        itemBinding.root.setOnClickListener { view ->
            listener.onClicked(mVehicle.vin)
        }
    }
}