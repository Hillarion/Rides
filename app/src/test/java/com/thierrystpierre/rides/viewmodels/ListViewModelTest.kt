package com.thierrystpierre.rides.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thierrystpierre.rides.data.api.VehicleApiManager
import com.thierrystpierre.rides.data.models.Vehicle
import com.thierrystpierre.rides.util.ErrorBus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {
    private val errBus =  spyk(ErrorBus())
    private val apiManager = mockk<VehicleApiManager>()
    private lateinit var viewModel: ListViewModel

    @get:Rule
    val instantExecutorRule =  InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = ListViewModel(apiManager, errBus)
        coEvery {apiManager.getVehicles(any(), any(), any())} returns Unit
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun isValidNull() {
        assertFalse(viewModel.isValid(null))
    }

    @Test
    fun isValidGood1() {
        assertTrue(viewModel.isValid(1.toUInt()))
    }

    @Test
    fun isValidGood2() {
        assertTrue(viewModel.isValid(50.toUInt()))
    }

    @Test
    fun isValidGood3() {
        assertTrue(viewModel.isValid(100.toUInt()))
    }

    @Test
    fun isValidBad1() {
        assertFalse(viewModel.isValid(0.toUInt()))
    }

    @Test
    fun isValidBad2() {
        assertFalse(viewModel.isValid(101.toUInt()))
    }

    @Test
    fun isValidBad3() {
        assertFalse(viewModel.isValid(200.toUInt()))
    }

    @Test
    fun fetchVehiclesGood() {
        viewModel.quantity = 4.toUInt()
        viewModel.fetchVehicles()
        coVerify(exactly = 1) { apiManager.getVehicles(any(), any(), any()) }
        coVerify(exactly = 0) { errBus.publish(any()) }
    }

    @Test
    fun fetchVehiclesBad1() {
        viewModel.quantity = 0.toUInt()
        viewModel.fetchVehicles()
        coVerify(exactly = 0) { apiManager.getVehicles(any(), any(), any()) }
        coVerify(exactly = 1) { errBus.publish(any()) }
    }

    @Test
    fun fetchVehiclesBad2() {
        viewModel.quantity = 101.toUInt()
        viewModel.fetchVehicles()
        coVerify(exactly = 0) { apiManager.getVehicles(any(), any(), any()) }
        coVerify(exactly = 1) { errBus.publish(any()) }
    }
}
