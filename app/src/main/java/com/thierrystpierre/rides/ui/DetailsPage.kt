package com.thierrystpierre.rides.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.databinding.PageDetailsBinding

class DetailsPage: Fragment() {
    private var _binding: PageDetailsBinding? = null

    lateinit var vehicle : Vehicle

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewVinValue.text = vehicle.vin
        binding.textviewMakeValue.text = vehicle.make_and_model
        binding.textviewColorValue.text = vehicle.color
        binding.textviewTypeValue.text = vehicle.car_type
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}