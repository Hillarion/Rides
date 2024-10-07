package com.thierrystpierre.rides.ui.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thierrystpierre.rides.data.models.SortedVehicle
import com.thierrystpierre.rides.databinding.VehicleRowBinding

class VehicleAdapter(private val listener : RowClickedListener)
    : RecyclerView.Adapter<VehicleViewHolder>() {

    private var vehicles = mutableListOf<SortedVehicle>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        Log.d("VehicleAdapter", "onCreateViewHolder()")
        return VehicleViewHolder(
            VehicleRowBinding.inflate((LayoutInflater.from(parent.context))),
            listener
        )
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        Log.d("VehicleAdapter", "binding position = $position")
        holder.configure(vehicles[position])
    }

    fun setData(newData : List<SortedVehicle>) {
        Log.d("VehicleAdapter", "setData data = ${newData.size}")
        vehicles = newData.toMutableList()
        notifyDataSetChanged()
    }
}