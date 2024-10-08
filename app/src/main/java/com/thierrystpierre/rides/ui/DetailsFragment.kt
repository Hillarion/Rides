package com.thierrystpierre.rides.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thierrystpierre.rides.R
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.databinding.FragmentDetailsBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val args : DetailsFragmentArgs by navArgs()
    private lateinit var vehicle : Vehicle

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        vehicle = args.vehicle
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