package com.thierrystpierre.rides.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.databinding.FragmentDetailsBinding
import javax.inject.Inject
import kotlin.math.min

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val args : DetailsFragmentArgs by navArgs()
    private lateinit var vehicle : Vehicle
    lateinit var pagerAdapter : RidePagerAdapter
    @Inject
    lateinit var detailsPage : DetailsPage
    @Inject
    lateinit var carbonEmissionPage : CarbonEmissionPage

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        vehicle = args.vehicle
        pagerAdapter = RidePagerAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsPage = DetailsPage()
        pagerAdapter.addFrag(detailsPage)
        carbonEmissionPage = CarbonEmissionPage()
        pagerAdapter.addFrag(carbonEmissionPage)
        detailsPage.vehicle = vehicle
        carbonEmissionPage.vehicle = vehicle

        binding.pager.adapter = pagerAdapter
        binding.pager.offscreenPageLimit = 2
    }

    override fun onStart() {
        super.onStart()
//        binding.pager.currentItem = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class RidePagerAdapter(containerFragment: DetailsFragment) : FragmentStateAdapter(containerFragment) {
        private val mFragmentList : MutableList<Fragment> = mutableListOf() //fragment arraylist

        override fun getItemCount(): Int = min(mFragmentList.size, 2)

        //adding fragments and title method
        fun addFrag(fragment: Fragment) {
            mFragmentList.add(fragment)
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }

    }

}