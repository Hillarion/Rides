package com.thierrystpierre.rides.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.thierrystpierre.rides.R
import com.thierrystpierre.rides.data.models.SortedVehicle
import com.thierrystpierre.rides.databinding.FragmentListBinding
import com.thierrystpierre.rides.ui.recycler.RowClickedListener
import com.thierrystpierre.rides.ui.recycler.VehicleAdapter
import com.thierrystpierre.rides.viewmodels.ListViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel : ListViewModel by viewModels()
    private var _binding: FragmentListBinding? = null
    private lateinit var button : Button
    private lateinit var inputField : EditText
    private lateinit var recyclerView : RecyclerView

    private val listener : RowClickedListener = object : RowClickedListener {
        override fun onClicked(vin: String) {
            Log.d("ListFragment", "RowClickedListener($vin) ")
            val bundle = Bundle()
            val vehicle = viewModel.getVehicle(vin)
            bundle.putParcelable("vehicle", vehicle)
            this@ListFragment.findNavController()
                .navigate(R.id.action_ListFragment_to_DetailsFragment, bundle)
        }
    }

    private val vehicleAdapter = VehicleAdapter(listener)

    private val vehicleObserver = Observer<List<SortedVehicle>> {
        Log.d("ListFragment", "vehicleObserver data = ${it.size}")
        vehicleAdapter.setData(it)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val inputWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            viewModel.quantity = s.toString().toIntOrNull()
            button.isEnabled = s.isNotEmpty()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = binding.buttonFetch
        inputField = binding.inputField
        recyclerView = binding.vehicleList
        recyclerView.adapter = vehicleAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL))

        viewModel.quantity?.let {
            inputField.setText(it.toString())
        }
        inputField.addTextChangedListener(inputWatcher)
        button.setOnClickListener {
            viewModel.fetchVehicles()

        }
        viewModel.sortedVehicles.observe(requireActivity(), vehicleObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}