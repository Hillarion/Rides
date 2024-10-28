package com.thierrystpierre.rides.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.databinding.PageCarbonEmissionBinding
import com.thierrystpierre.rides.viewmodels.VehicleViewModel

class CarbonEmissionPage : Fragment() {
    private var _binding: PageCarbonEmissionBinding? = null
    private val viewModel : VehicleViewModel by viewModels()

    lateinit var vehicle : Vehicle
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageCarbonEmissionBinding.inflate(inflater, container, false)
        viewModel.vehicle = vehicle
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.carbonEmissionValue.text = viewModel.getCarbonEmission().toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}