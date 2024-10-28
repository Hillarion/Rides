package com.thierrystpierre.rides.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import android.view.Menu
import android.view.MenuItem
import com.thierrystpierre.rides.R
import com.thierrystpierre.rides.databinding.ActivityMainBinding
import com.thierrystpierre.rides.util.DialogUtils
import com.thierrystpierre.rides.util.ErrorBus
import com.thierrystpierre.rides.util.ErrorStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var errorBus : ErrorBus
    private var receiveJob : Job? = null
    private var mJob: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private val errorConsumer = { error : ErrorStatus ->
        DialogUtils.createNoticeDialog(
            this,
            error.title ?: "", error.message
        ).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = findNavController(R.id.nav_host_fragment_content_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        receiveJob = launch(Dispatchers.Main) {
            errorBus.consume(errorConsumer)
        }
    }

    override fun onPause() {
        receiveJob?.cancel()
        super.onPause()
    }
}