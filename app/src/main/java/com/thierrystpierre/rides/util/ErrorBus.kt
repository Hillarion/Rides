package com.thierrystpierre.rides.util

import kotlinx.coroutines.flow.MutableSharedFlow

class ErrorBus {

    private val channel = MutableSharedFlow<ErrorStatus>(3)

    fun publish(error : ErrorStatus) {
            channel.tryEmit(error)
        }


    suspend fun consume(consumer : (ErrorStatus) -> Unit) {
        channel.collect(consumer)
    }
}

data class ErrorStatus(val title : String?, val message : String)