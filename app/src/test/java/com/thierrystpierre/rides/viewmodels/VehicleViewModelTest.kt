package com.thierrystpierre.rides.viewmodels

import com.thierrystpierre.rides.data.models.Vehicle
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class VehicleViewModelTest {

    private val fakeVehicle = mockk<Vehicle>()
    private  val viewModel = VehicleViewModel()

    @Before
    fun setUp() {
        viewModel.vehicle = fakeVehicle
    }

    @Test
    fun getCarbonEmissionBelow5000() {
        every {fakeVehicle.kilometrage} returns 1000
        assertEquals(null, viewModel.getCarbonEmission(), 1000.0, 0.0)
    }

    @Test
    fun getCarbonEmissionAbove5000_1() {
        every {fakeVehicle.kilometrage} returns 6000
        assertEquals(null, viewModel.getCarbonEmission(), 6500.0, 0.0)
    }

    @Test
    fun getCarbonEmissionAbove5000_2() {
        every {fakeVehicle.kilometrage} returns 10000
        assertEquals(null, viewModel.getCarbonEmission(), 12500.0, 0.0)
    }

    @Test
    fun simpleRate() {
        assertEquals(null, viewModel.simpleRate(1), 1.0, 0.0)
        assertEquals(null, viewModel.simpleRate(3), 3.0, 0.0)
        assertEquals(null, viewModel.simpleRate(10), 10.0, 0.0)
    }

    @Test
    fun highRate() {
        assertEquals(null, viewModel.highRate(1), 1.5, 0.0)
        assertEquals(null, viewModel.highRate(3), 4.5, 0.0)
        assertEquals(null, viewModel.highRate(10), 15.0, 0.0)
    }
}